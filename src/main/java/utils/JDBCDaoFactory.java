package utils;

import dao.UserDAO;
import dao.UserDaoJdbcImpl;

public class JDBCDaoFactory extends UserDaoFactory {
    @Override
    public UserDAO createDao() {
        return new UserDaoJdbcImpl();
    }
}
