package com.booking.tag.date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateInputTag extends TagSupport {

    private static final DateTimeFormatter HTML_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String HTML_INPUT_TAG = "<input type=\"date\" name=\"%s\"  min=\"%s\" value=\"%s\" required>";

    private String name;
    private MinimumDateAttribute minimumDate;

    public void setName(String name) {
        this.name = name;
    }

    public void setMinimumDate(String value) throws JspException {
        minimumDate = MinimumDateAttribute.getFromString(value);
    }

    @Override
    public int doStartTag() throws JspException {
        String date = getMinimumDateInHtmlFormat(); // min value and default value
        try {
            JspWriter out = pageContext.getOut();
            String inputTag = String.format(HTML_INPUT_TAG, name, date, date);
            out.write(inputTag);
        } catch (IOException e) {
            throw new JspException(e.getMessage(), e);
        } // JspWriter doesn't need to be closed
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    private String getMinimumDateInHtmlFormat() throws JspException {
        String date;
        LocalDateTime today = LocalDateTime.now();
        switch (minimumDate) {
            case TODAY:
                date = today.format(HTML_DATE_FORMATTER);
                break;
            case TOMORROW:
                LocalDateTime tomorrow = today.plusDays(1);
                date = tomorrow.format(HTML_DATE_FORMATTER);
                break;
            default:
                throw new JspException("Invalid minimum date");
        }
        return date;
    }

}
