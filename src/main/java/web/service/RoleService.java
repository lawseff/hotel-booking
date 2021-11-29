package web.service;

import web.entity.User;

public interface RoleService {

    boolean isUser();

    boolean isAdmin();

    User getCurrentUser();

}
