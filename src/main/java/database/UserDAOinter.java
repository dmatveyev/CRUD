package database;

import java.util.List;
//interface
public interface UserDAOinter {
    void insert(User user);

    String getUserId(String login, String password);

    User get(String id);

    void delete(String id);

    List<User> getUsers();

    void update(User user);
}
