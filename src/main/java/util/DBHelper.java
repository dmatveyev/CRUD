package util;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import model.User;
import model.UserSession;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;


public class DBHelper {

    public static DBHelper dbHelper;
    private Properties properties;
    private final SessionFactory sessionFactory;

    private DBHelper() {
        final String propertiesPath = "D:\\apache-tomcat-8.0.48\\webapps\\f.properties";
        properties = new Properties();
        try (InputStream in = new FileInputStream(propertiesPath)){
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        dataSource.setURL(properties.getProperty("url"));
        dataSource.setUser(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
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
        configuration.addAnnotatedClass(UserSession.class);
        configuration.setProperty("hibernate.dialect", properties.getProperty("hibernate.dialect"));
        configuration.setProperty("hibernate.connection.driver_class", properties.getProperty("hibernate.connection.driver_class"));
        configuration.setProperty("hibernate.connection.url", properties.getProperty("url"));
        configuration.setProperty("hibernate.connection.username", properties.getProperty("username"));
        configuration.setProperty("hibernate.connection.password", properties.getProperty("password"));
        configuration.setProperty("hibernate.show_sql", properties.getProperty("hibernate.show_sql"));
        configuration.setProperty("hibernate.hbm2ddl.auto", properties.getProperty("hibernate.hbm2ddl.auto")); //
        return configuration;
    }



    private SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
