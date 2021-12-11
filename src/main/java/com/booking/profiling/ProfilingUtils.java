package com.booking.profiling;

import com.booking.profiling.model.JreInfo;
import com.booking.profiling.model.SystemInfo;

public class ProfilingUtils {

    private static final double MB_DIVISOR = 1024.;

    private static final String OS_ARCHITECTURE = "os.arch";
    private static final String OS_NAME = "os.name";
    private static final String OS_VERSION = "os.version";

    private static final String JAVA_VENDOR = "java.vendor";
    private static final String JAVA_VERSION = "java.version";

    public static double getMemoryUsageInMb() {
        long totalBytes = Runtime.getRuntime().totalMemory();
        long freeBytes = Runtime.getRuntime().freeMemory();
        long diff = totalBytes - freeBytes;
        return diff / MB_DIVISOR;
    }

    public static SystemInfo getSystemInfo() {
        String architecture = System.getProperty(OS_ARCHITECTURE);
        String name = System.getProperty(OS_NAME);
        String version = System.getProperty(OS_VERSION);
        return SystemInfo.builder()
                .osArchitecture(architecture)
                .osName(name)
                .osVersion(version)
                .build();
    }

    public static JreInfo getJreInfo() {
        String vendor = System.getProperty(JAVA_VENDOR);
        String version = System.getProperty(JAVA_VERSION);
        return JreInfo.builder()
                .vendor(vendor)
                .version(version)
                .build();
    }

}
