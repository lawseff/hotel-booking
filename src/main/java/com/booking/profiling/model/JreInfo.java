package com.booking.profiling.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JreInfo {

    private final String vendor;

    private final String version;

}
