package database.dao;

public class HibernateDaoFactory extends UserDAOFactory {
    @Override
    public UserDAO createDao() {
        return new UserDAOHibernate();
    }
}
