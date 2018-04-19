package com.denis.util;


import com.denis.dao.UserDAO;

/**
 * Created by Денис on 17.04.2018.
 */
public abstract class UserDaoFactory {

    public abstract UserDAO createDao();
}
