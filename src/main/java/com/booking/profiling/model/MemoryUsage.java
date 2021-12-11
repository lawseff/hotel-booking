package com.booking.profiling.model;

import java.time.Instant;
import lombok.Data;

@Data
public class MemoryUsage {

    private final Instant instant;

    private final double usageInMb;

}
