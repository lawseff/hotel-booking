package com.booking.profiling.report;

import com.booking.profiling.model.ProfilingReport;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public void generateReport(ProfilingReport report) {
        System.out.println(report);
    }

}
