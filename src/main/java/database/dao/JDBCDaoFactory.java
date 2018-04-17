package database.dao;

public class JDBCDaoFactory extends UserDAOFactory {
    @Override
    public UserDAO createDao() {
        return new UserDaoJDBC();
    }
}
