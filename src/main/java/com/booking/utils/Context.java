package com.booking.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class Context implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static <T> T getBean(final Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static String getProperty(final String name) {
        return applicationContext.getEnvironment().getProperty(name);
    }

    public static <T> T getProperty(final String name, final Class<T> clazz) {
        return applicationContext.getEnvironment().getProperty(name, clazz);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        Context.applicationContext = applicationContext;
        System.setProperty("system.arch", System.getProperty("os.arch"));
        // JDK bug
        System.setProperty("system.name", "Windows 8.1".equals(System.getProperty("os.name")) ? "Windows" : System.getProperty("os.name"));
        System.setProperty("system.version", "6.3".equals(System.getProperty("os.version")) ? "10" : System.getProperty("os.version"));
    }


}
