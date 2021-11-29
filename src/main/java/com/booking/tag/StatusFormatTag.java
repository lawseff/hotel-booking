package com.booking.tag;

import com.booking.entity.reservation.ReservationStatus;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class StatusFormatTag extends TagSupport {

    private static final String STATUS_WAITING = "status_waiting";
    private static final String STATUS_APPROVED = "status_approved";
    private static final String STATUS_PAID = "status_paid";
    private static final String STATUS_CHECKED_IN = "status_checked_in";
    private static final String STATUS_CHECKED_OUT = "status_checked_out";
    private static final String STATUS_CANCELLED = "status_cancelled";

    private ReservationStatus status;

    public void setStatus(String statusParameter) {
        status = ReservationStatus.valueOf(statusParameter);
    }

    @Override
    public int doStartTag() throws JspException {
        String attribute;
        switch (status) {
            case WAITING:
                attribute = STATUS_WAITING;
                break;
            case APPROVED:
                attribute = STATUS_APPROVED;
                break;
            case PAID:
                attribute = STATUS_PAID;
                break;
            case CHECKED_IN:
                attribute = STATUS_CHECKED_IN;
                break;
            case CHECKED_OUT:
                attribute = STATUS_CHECKED_OUT;
                break;
            case CANCELLED:
                attribute = STATUS_CANCELLED;
                break;
            default:
                throw new JspException("Invalid status: " + status);
        }
        try {
            JspWriter out = pageContext.getOut();
            out.print(pageContext.findAttribute(attribute));
        } catch (IOException e) {
            throw new JspException(e.getMessage(), e);
        } // JspWriter doesn't need to be closed
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }

}
