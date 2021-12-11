package com.booking.profiling.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ProfilingReport {

    private JreInfo jreInfo;

    private SystemInfo systemInfo;

    private ExecutionInfo executionInfo;

}
