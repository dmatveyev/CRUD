package util;


import dao.*;
/**
 * Created by Денис on 17.04.2018.
 */
public abstract class UserDaoFactory {

    public abstract UserDAO createDao();
}
