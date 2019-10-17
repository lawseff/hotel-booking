package com.epam.booking.utils.data.loader;

import com.epam.booking.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;

public interface PageDataLoader {

    void loadDataToSession(HttpServletRequest request) throws ServiceException;

}
