package utils;

import dao.*;

public class JDBCDaoFactory extends UserDaoFactory {
    @Override
    public UserDAO createDao() {
        return new UserDaoJdbcImpl();
    }
}
