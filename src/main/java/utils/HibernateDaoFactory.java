package utils;

import dao.UserDAO;
import dao.UserDaoHibernateImpl;

public class HibernateDaoFactory extends UserDaoFactory {
    @Override
    public UserDAO createDao() {
        return new UserDaoHibernateImpl();
    }
}
