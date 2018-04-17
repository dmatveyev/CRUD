package database;

import org.hibernate.*;

import java.util.List;

public class UserDAOHibernate implements UserDAOinter {

    private SessionFactory sessionFactory;
    private final DBServiceHibernate connectDB;

    public UserDAOHibernate() {
        connectDB = new DBServiceHibernate();
        sessionFactory = connectDB.getSessionFactory();

    }

    @Override
    public void insert(User user) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            session.close();
        }catch (HibernateException e ) {
            e.printStackTrace();
        }

    }

    @Override
    public String getUserId(String login, String password) {
        return null;
    }

    @Override
    public User get(String id) {
        User user = null;
        try {
            Session session = sessionFactory.openSession();
            user = (User) session.get(User.class, id);
            session.close();
        }catch (HibernateException e) {
            e.printStackTrace();
        }
        return user;

    }

    @Override
    public void delete(String id) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE from User WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
            session.close();
        }catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getUsers() {
        List<User>  users= null;
        try {
            Session session = sessionFactory.openSession();
            Query query = session.createQuery("from User");
            users = query.list();
            session.close();
        }catch (HibernateException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void update(User user) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Query query =
                    session.createQuery("UPDATE User SET login = :login, password = :password where id = :id ");
            query.setParameter("login", user.getLogin())
                    .setParameter("password", user.getPassword())
                    .setParameter("id", user.getId())
                    .executeUpdate();
            transaction.commit();
            session.close();
        }catch (HibernateException e) {
            e.printStackTrace();
        }

    }
}
