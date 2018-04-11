package database;

import org.h2.jdbcx.JdbcDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBService {

    private final Properties properties;
    private Logger logger;

    public DBService() {
        logger = Logger.getLogger("DBService");
        properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("general.properties"));
        } catch (final IOException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public Connection getConnection() throws SQLException {

        // TODO: 12.04.2018 Переписать с использованием пула коннектов tomcat
        final String driver = properties.getProperty("dbdriver");
        if (driver.equals("h2"))
            return getH2Connection();
        return null;
    }

    private Connection getH2Connection() {
        // TODO: 12.04.2018 Использовать пул конектов tomcat
        try {
            final String url = properties.getProperty("h2.url");
            final String name = properties.getProperty("h2.username");
            final String pass = properties.getProperty("h2.password");

            final JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(pass);
            final Connection connection = ds.getConnection();
            try (Statement st = connection.createStatement()) {
                st.executeUpdate("create table if not exists users(id varchar(255), login varchar(255) ,password varchar(255))");
                st.executeUpdate("create table if not exists user_session(id varchar(255), session varchar(255))");
            } catch (final SQLException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
            }
            return connection;
        } catch (final SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
        return null;
    }
}
