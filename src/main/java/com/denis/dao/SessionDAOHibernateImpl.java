package com.denis.dao;

import com.denis.model.UserSession;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionDAOHibernateImpl implements SessionDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public SessionDAOHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public UserSession get(long userId) {
        UserSession userSession = null;
        try {
            Session session = sessionFactory.openSession();
            Query query = session.createQuery("from UserSession where userId =:userId");
            query.setParameter("userId", userId);

            if(query.list().size() != 0) {
                userSession = (UserSession) query.list().get(0);
            }
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return userSession;
    }

    @Override
    public void insert(UserSession userSession) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(userSession);
            transaction.commit();
            session.close();
        }catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(UserSession userSession) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(userSession);
        transaction.commit();
        session.close();
    }

    @Override
    public UserSession getUserId(String uuid) {
        UserSession userSession = null;
        try {
            Session session = sessionFactory.openSession();
            Query query = session.createQuery("from UserSession where uuid =:uuid");
            query.setParameter("uuid", uuid);

            if(query.list().size() != 0) {
                userSession = (UserSession) query.list().get(0);
            }
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return userSession;
    }
}
