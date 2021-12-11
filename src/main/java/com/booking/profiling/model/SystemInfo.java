package com.booking.profiling.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SystemInfo {

    private final String osArchitecture;

    private final String osName;

    private final String osVersion;

}
