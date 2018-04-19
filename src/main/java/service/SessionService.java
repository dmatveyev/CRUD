package service;

import model.User;
import model.UserSession;

public interface SessionService {

    void createSession (User user);
    UserSession get (long userId);
    void insert (UserSession userSession);
    void delete(UserSession userSession);
    UserSession getSessionByUuid(String uuid);
}
