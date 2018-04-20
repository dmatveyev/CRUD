package com.denis.dao;

import com.denis.model.User;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("userDaoHibernateImpl")
public class UserDaoHibernateImpl implements UserDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public UserDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void insert(User user) {
        try {
            Session session = getSession();
            session.save(user);
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
            user = (User) session.get(User.class, id);
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
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
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
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public User getUserByLoginPassword(String login, String password) {
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

    @Override
    public User getUserByLogin(String login) {
        User user = null;
        try {
            Session session = getSession();
            Query query = session.createQuery("from User where login =:login");
            query.setParameter("login", login);
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
