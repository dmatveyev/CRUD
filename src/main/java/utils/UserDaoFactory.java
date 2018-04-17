package utils;

import dao.UserDAO;

/**
 * Created by Денис on 17.04.2018.
 */
public abstract class UserDaoFactory {

    public abstract UserDAO createDao();
}
