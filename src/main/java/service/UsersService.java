package service;

import model.User;
import util.DaoFactories;
import util.HibernateDaoFactory;
import util.JDBCDaoFactory;
import util.UserDaoFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public interface UsersService {



    UserDaoFactory getDaoFactory(Properties properties);



    long registerUser(final User user);

    void deleteUser(User user);

    List<User> getUsers();

    User createUser(final String login, final String password);

    void updateUser(User user);

    User getUserById(long id);

    User getUserByLogin(String login, String password);
}
