package com.booking.profiling.time;

import com.booking.profiling.ProfilingUtils;
import com.booking.profiling.model.MemoryUsage;
import com.booking.profiling.model.ExecutionInfo;
import com.booking.profiling.model.ProfilingReport;
import com.booking.profiling.report.ReportService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProfilingAspect {

    @Autowired
    private ReportService reportService;

    @Value("${profiling.memory.measure.step.millis}")
    private long memoryMeasureStepMillis;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Around("@annotation(Profiled)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        List<MemoryUsage> memoryUsages = new ArrayList<>();
        Future<?> punctuation = executorService.submit(() -> punctuateMemoryUsage(memoryUsages));
        try {
            return joinPoint.proceed();
        } finally {
            long executionTime = System.currentTimeMillis() - start;
            punctuation.cancel(true);

            ExecutionInfo executionInfo = ExecutionInfo.builder()
                    .executionTimeMillis(executionTime)
                    .methodName(joinPoint.getSignature().toLongString())
                    .memoryUsageByTime(memoryUsages)
                    .build();

            ProfilingReport report = ProfilingReport.builder()
                    .jreInfo(ProfilingUtils.getJreInfo())
                    .systemInfo(ProfilingUtils.getSystemInfo())
                    .executionInfo(executionInfo)
                    .build();
            reportService.generateReport(report);
        }
    }

    @SneakyThrows
    private void punctuateMemoryUsage(List<MemoryUsage> memoryUsages) {
        while (true) {
            Instant now = Instant.now();
            double value = ProfilingUtils.getMemoryUsageInMb();
            MemoryUsage memoryUsage = new MemoryUsage(now, value);
            memoryUsages.add(memoryUsage);
            TimeUnit.MILLISECONDS.sleep(memoryMeasureStepMillis);
        }
    }

}
