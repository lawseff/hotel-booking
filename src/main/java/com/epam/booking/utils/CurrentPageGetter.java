package com.epam.booking.utils;

import javax.servlet.http.HttpServletRequest;

public class CurrentPageGetter {

    private static final String REFERER  = "referer";

    public String getCurrentPage(HttpServletRequest request) {
        String header = request.getHeader(REFERER);
        String serverUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                request.getContextPath();
        return header.replace(serverUrl, "");
    }

}
