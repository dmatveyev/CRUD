package dao;

import utils.DBHelper;
import model.User;
import org.hibernate.*;

import java.util.List;

public class UserDaoHibernateImpl implements UserDAO {

    private SessionFactory sessionFactory;
    private final DBHelper connectDB;

    public UserDaoHibernateImpl() {
        connectDB = DBHelper.getInstance();
        sessionFactory = connectDB.getSessionFactory();

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
    public User get(String id) {
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
    public void delete(String id) {
        try {
            Session session = getSession();
            Transaction transaction = getTransaction(session);
            session.createQuery("DELETE from User WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
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
                    session.createQuery("UPDATE User SET login = :login, password = :password where id = :id ");
            query.setParameter("login", user.getLogin())
                    .setParameter("password", user.getPassword())
                    .setParameter("id", user.getId())
                    .executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }
}
