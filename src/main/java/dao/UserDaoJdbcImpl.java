package dao;

import utils.DBHelper;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJdbcImpl implements UserDAO {

    private final DBHelper connectDB;

    private final Logger logger;

    public UserDaoJdbcImpl() {
        logger = Logger.getLogger("userdao");
        connectDB = DBHelper.getInstance();
    }

    public User get(final String id) {
        User user = null;
        try (Connection conn = connectDB.getConnection();
             PreparedStatement st = conn.prepareStatement("select * from users where id = ?")) {
            st.setString(1, id);
            try (ResultSet res = st.executeQuery()) {
                res.next();
                user = createUser(res);
            } catch (final SQLException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
            }

        } catch (final SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
        return user;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = connectDB.getConnection();
             PreparedStatement st = conn.prepareStatement("select * from users")) {
            try (ResultSet res = st.executeQuery()) {
                while (res.next()) {
                    User user = createUser(res);
                    users.add(user);
                }
            } catch (final SQLException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
            }

        } catch (final SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
        return users;
    }

    private User createUser(ResultSet res) throws SQLException {
        User user = new User();
        user.setId(res.getString(1));
        user.setLogin(res.getString(2));
        user.setPassword(res.getString(3));
        return user;
    }


    public String getUserId(final String login, final String password) {
        String userId = null;
        try (Connection conn = connectDB.getConnection();
             PreparedStatement st = conn.prepareStatement("select * from users where login = ? and password = ?")) {
            st.setString(1, login);
            st.setString(2, password);
            try (ResultSet res = st.executeQuery()) {
                if (res.next()) {
                    userId = res.getString(1);
                }
            }
        } catch (final SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
        return userId;
    }

    public void insert(final User t) {
        try (Connection conn = connectDB.getConnection();
             PreparedStatement statement = conn.prepareStatement("insert into users (id,login, password) values (?,?,?)")
        ) {
            statement.setString(1, t.getId());
            statement.setString(2, t.getLogin());
            statement.setString(3, t.getPassword());
            statement.executeUpdate();
        } catch (final SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }


    public void update(final User t) {
        try (Connection conn = connectDB.getConnection();
             PreparedStatement statement = conn.prepareStatement("update users set login = ?, password = ? where id = ? ")) {
            statement.setString(1, t.getLogin());
            statement.setString(2, t.getPassword());
            statement.setString(3, t.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserByLogin(String login, String password) {
        User user = null;
        try (Connection conn = connectDB.getConnection();
             PreparedStatement st = conn.prepareStatement("select * from users where login = ? and password = ?")) {
            st.setString(1, login);
            st.setString(2, password);
            try (ResultSet res = st.executeQuery()) {
                if (res.next()) {
                    user.setId(res.getString(1));
                    user.setLogin(res.getString(2));
                    user.setPassword(res.getString(3));
                    user.setRole(res.getString(4));
                }
            }
        } catch (final SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
        return user;
    }


    public void delete(final String userId) {
        try (Connection conn = connectDB.getConnection();
             PreparedStatement st = conn.prepareStatement("delete from users where id = ?")) {
            st.setString(1, userId);
            st.executeUpdate();
        } catch (final SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }
}
