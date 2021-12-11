package com.booking.profiling.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ProfilingReport {

    private JreInfo jreInfo;

    private SystemInfo systemInfo;

    private ExecutionInfo executionInfo;

    private Instant generationTime;

}
