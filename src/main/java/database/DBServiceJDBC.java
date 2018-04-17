package database;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.h2.jdbcx.JdbcDataSource;




import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DBServiceJDBC {


    private Logger logger;

    public DBServiceJDBC() {
        logger = Logger.getLogger("DBServiceJDBC");
    }


    public Connection getConnection()  {
        final String driver ="sqlserver";
        if (driver.equals("sqlserver"))
            return getSQLServerConnection();
        if (driver.equals("h2"))
            return getH2Connection();
        return null;
    }


    private Connection getSQLServerConnection() {
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
            logger.log(Level.WARNING, e.getMessage(), e);
        }
        return null;
    }

    private Connection getH2Connection() {
        // TODO: 12.04.2018 Использовать пул конектов tomcat
        try {
            final String url = "jdbc:h2:./h2db";
            final String name = "sa";
            final String pass = "";

            final JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(pass);
            final Connection connection = ds.getConnection();
            try (Statement st = connection.createStatement()) {
                st.executeUpdate("create table if not exists users(id varchar(255), login varchar(255) ,password varchar(255))");
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
