package database;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.util.logging.Level;

public class DBHelper {

    public static DBHelper dbHelper;

    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";

    private final SessionFactory sessionFactory;

    private DBHelper() {
        Configuration configuration = getMsSqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    public static DBHelper getInstance() {
        if (dbHelper == null) {
            dbHelper = new DBHelper();
        }
        return dbHelper;
    }

    public Connection getConnection()  {
        final SQLServerDataSource dataSource = new SQLServerDataSource();
        final String url = "jdbc:sqlserver://localhost:1433;databaseName=dendb";
        final String name = "sa";
        final String pass = "magenta";
        dataSource.setURL(url);
        dataSource.setUser(name);
        dataSource.setPassword(pass);
        try {
            return dataSource.getConnection();
        } catch (final SQLServerException e) {
            e.printStackTrace();
        }
        return null;
    }



    private Configuration getMsSqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        configuration.setProperty("hibernate.connection.url", "jdbc:sqlserver://localhost:1433;databaseName=dendb");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "magenta");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto); //
        return configuration;
    }



    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
