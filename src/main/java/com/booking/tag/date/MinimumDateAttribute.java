package com.booking.tag.date;

import javax.servlet.jsp.JspException;

public enum MinimumDateAttribute {

    TODAY, TOMORROW;

    public static MinimumDateAttribute getFromString(String value) throws JspException {
        MinimumDateAttribute minimumDate;
        switch (value) {
            case "today":
                minimumDate = TODAY;
                break;
            case "tomorrow":
                minimumDate = TOMORROW;
                break;
            default:
                throw new JspException("Invalid attribute value: " + value);
        }
        return minimumDate;
    }

}
