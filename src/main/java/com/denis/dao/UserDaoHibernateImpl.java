package com.denis.dao;

import com.denis.model.User;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDaoHibernateImpl")
public class UserDaoHibernateImpl implements UserDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public UserDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void insert(User user) {
        try {
            Session session = getSession();
            Transaction transaction = getTransaction(session);
            session.save(user);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    private Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public String getUserId(String login, String password) {
        return null;
    }

    @Override
    public User get(long id) {
        User user = null;
        try {
            Session session = getSession();
            Transaction transaction = getTransaction(session);
            user = (User) session.get(User.class, id);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return user;

    }

    @Override
    public void delete(User user) {
        try {
            Session session = getSession();
            Transaction transaction = getTransaction(session);
            session.delete(user);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    private Transaction getTransaction(Session session) {
        return session.beginTransaction();
    }

    @Override
    public List<User> getUsers() {
        List<User> users = null;
        try {
            Session session = getSession();
            Query query = session.createQuery("from User");
            users = query.list();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void update(User user) {
        try {
            Session session = getSession();
            Transaction transaction = getTransaction(session);
            Query query =
                    session.createQuery("UPDATE User SET login = :login, password = :password, role=:role where id = :id ");
            query.setParameter("login", user.getLogin())
                    .setParameter("password", user.getPassword())
                    .setParameter("id", user.getId())
                    .setParameter("role", user.getRole())
                    .executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public User getUserByLogin(String login, String password) {
        User user = null;
        try {
            Session session = getSession();
            Query query = session.createQuery("from User where login =:login and password = :password");
            query.setParameter("login", login);
            query.setParameter("password", password);
            if(query.list().size() != 0) {
                user = (User) query.list().get(0);
            }
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return user;
    }
}
