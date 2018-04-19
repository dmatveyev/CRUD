package service;

import dao.SessionDAO;
import dao.SessionDAOHibernateImpl;
import model.User;
import model.UserSession;

import java.util.UUID;

public class SessionServiceImpl implements SessionService{

    public static SessionServiceImpl sessionService;
    private SessionDAO sessionDAO;

    private SessionServiceImpl() {
        sessionDAO = new SessionDAOHibernateImpl();
    }

    public static SessionServiceImpl getInstanse(){
        if (sessionService == null) {
            sessionService = new SessionServiceImpl();
        }
        return sessionService;
    }

    public void createSession (User user) {
        UserSession session = new UserSession();
        session.setUserId(user.getId());
        session.setUuid(UUID.randomUUID().toString());
        insert(session);

    }

    public UserSession get (long userId) {
        return sessionDAO.get(userId);
    }
    public void insert (UserSession userSession) {
        sessionDAO.insert(userSession);
    }
    public void delete(UserSession userSession){
        sessionDAO.delete(userSession);
    }

    public UserSession getSessionByUuid(String uuid) {
        return sessionDAO.getUserId(uuid);
    }
}
