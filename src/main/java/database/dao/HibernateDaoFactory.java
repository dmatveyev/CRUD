package database.dao;

public class HibernateDaoFactory extends UserDaoFactory {
    @Override
    public UserDAO createDao() {
        return new UserDaoHibernateImpl();
    }
}
