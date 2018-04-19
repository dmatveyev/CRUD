package com.denis.service;

import com.denis.dao.SessionDAO;
import com.denis.dao.SessionDAOHibernateImpl;
import com.denis.model.User;
import com.denis.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SessionServiceImpl implements SessionService{

    private SessionDAO sessionDAO;
    @Autowired
    public SessionServiceImpl(SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
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
