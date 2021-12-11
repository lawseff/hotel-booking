package com.booking.profiling.report;

import com.booking.profiling.model.MemoryUsage;
import com.booking.profiling.model.ProfilingReport;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH'h'mm'm'ss's'");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

    private static final String TEMPLATE = getTemplate();

    @Value("${profiling.report.base.folder}")
    private String reportBaseFolder;

    @SneakyThrows
    @Override
    public void generateReport(ProfilingReport report) {
        String folder = reportBaseFolder + "/rep_" + fromInstant(report.getGenerationTime()).format(TIMESTAMP_FORMATTER);
        Files.createDirectories(Paths.get(folder));
        saveChart(report, folder);
        saveHtmlReport(report, folder);
    }

    private static String getTemplate() {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            try (InputStream stream = loader.getResourceAsStream("reportTemplate.html")) {
                try (InputStreamReader reader = new InputStreamReader(stream)) {
                    return new BufferedReader(reader).lines().collect(Collectors.joining(System.lineSeparator()));
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private void saveChart(ProfilingReport report, String path) throws IOException {
        List<MemoryUsage> memoryUsageByTime = report.getExecutionInfo().getMemoryUsageByTime();
        TimeSeries timeSeries = new TimeSeries("Memory usage");
        memoryUsageByTime.forEach(usage -> timeSeries.add(
                new Millisecond(Date.from(usage.getInstant())),
                (int) usage.getUsageInMb())
        );
        JFreeChart chart = ChartFactory.createTimeSeriesChart("", "Time", "MB", new TimeSeriesCollection(timeSeries));

        final File file = new File(path + "/memory_usage.jpg");
        ChartUtils.saveChartAsJPEG(file, chart, 800, 600);
    }

    private void saveHtmlReport(ProfilingReport report, String folder) throws IOException {
        String html = String.format(TEMPLATE,
                fromInstant(report.getGenerationTime()).format(DATE_FORMATTER),
                // System info
                report.getSystemInfo().getOsName(),
                report.getSystemInfo().getOsVersion(),
                report.getSystemInfo().getOsArchitecture(),
                // JRE info
                report.getJreInfo().getVendor(),
                report.getJreInfo().getVersion(),
                // Execution info
                report.getExecutionInfo().getMethodName(),
                report.getExecutionInfo().getExecutionTimeMillis() / 1000.
        );
        Files.write(Paths.get(folder + "/report.html"), html.getBytes(StandardCharsets.UTF_8));
    }

    private LocalDateTime fromInstant(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

}
