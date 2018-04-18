package dao;

import model.User;
import model.UserSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.DBHelper;

public class SessionDAOHibernateImpl implements SessionDAO {
    private SessionFactory sessionFactory;
    private final DBHelper connectDB;

    public SessionDAOHibernateImpl() {
        connectDB = DBHelper.getInstance();
        sessionFactory = connectDB.getSessionFactory();

    }


    @Override
    public UserSession get(long userId) {
        UserSession userSession = null;
        try {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        userSession = (UserSession) session.get(UserSession.class,userId);
        transaction.commit();
        session.close();
        }catch (HibernateException e) {
            e.printStackTrace();
        }
        return userSession;
    }

    @Override
    public void insert(UserSession userSession) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(userSession);
            transaction.commit();
            session.close();
        }catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(UserSession userSession) {

    }
}
