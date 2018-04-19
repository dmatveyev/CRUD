package com.denis.util;

import com.denis.dao.*;

public class HibernateDaoFactory extends UserDaoFactory {
    @Override
    public UserDAO createDao() {
        return new UserDaoHibernateImpl();
    }
}
