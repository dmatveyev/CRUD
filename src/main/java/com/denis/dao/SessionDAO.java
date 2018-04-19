package com.denis.dao;


import com.denis.model.UserSession;

public interface SessionDAO {
    UserSession get (long userId);
    void insert (UserSession userSession);
    void delete(UserSession userSession);

    UserSession getUserId(String uuid);
}
