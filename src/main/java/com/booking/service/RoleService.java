package com.booking.service;

import com.booking.entity.User;

public interface RoleService {

    boolean isUser();

    boolean isAdmin();

    User getCurrentUser();

}
