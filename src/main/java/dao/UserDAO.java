package dao;

import model.User;
import java.util.List;

public interface UserDAO {
    void insert(User user);

    String getUserId(String login, String password);

    User get(long id);

    void delete(String id);

    List<User> getUsers();

    void update(User user);

    User getUserByLogin(String login, String password);
}
