package services;

import dao.SessionDAO;
import dao.SessionDAOHibernateImpl;
import dao.UserDAO;
import model.User;
import model.UserSession;

import java.util.UUID;

public class SessionService {

    public static SessionService sessionService;
    private SessionDAO sessionDAO;

    private SessionService() {
        sessionDAO = new SessionDAOHibernateImpl();
    }

    public static SessionService getInstanse(){
        if (sessionService == null) {
            sessionService = new SessionService();
        }
        return sessionService;
    }

    public void createSession (User user) {
        UserSession session = new UserSession();
        session.setUserId(user.getId());
        session.setUser(user);
        session.setUuid(UUID.randomUUID().toString());
        user.setUserSession(session);
        insert(session);

    }

    public UserSession get (long userId) {
        return sessionDAO.get(userId);
    }
    void insert (UserSession userSession) {
        sessionDAO.insert(userSession);
    }
    void delete(UserSession userSession){
        sessionDAO.delete(userSession);
    }

    public UserSession getSessionByUuid(String uuid) {
        return sessionDAO.getUserId(uuid);
    }
}
