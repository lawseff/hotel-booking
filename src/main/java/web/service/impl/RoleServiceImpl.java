package web.service.impl;

import web.service.RoleService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import web.entity.User;

public class RoleServiceImpl implements RoleService {

    @Override
    public boolean isUser() {
        return getUser() != null;
    }

    @Override
    public boolean isAdmin() {
        User user = getUser();
        return user != null && user.isAdmin();
    }

    private User getUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute("user");
    }

}
