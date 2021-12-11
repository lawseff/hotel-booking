package com.booking.profiling.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExecutionInfo {

    private String methodName;

    private final long executionTimeMillis;

    private List<MemoryUsage> memoryUsageByTime;

}
