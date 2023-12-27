package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;


public class Util {
    private static final String URL = "jdbc:postgresql://localhost:5432/user_db";
    private static final String PASSWORD = "postgres";
    private static final String USER = "postgres";
    private static final Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        Properties prop = new Properties();
        prop.setProperty("connection.driver_class", "org.postgresql.Driver");
        prop.setProperty("hibernate.connection.url", URL);
        prop.setProperty("hibernate.connection.username", USER);
        prop.setProperty("hibernate.connection.password", PASSWORD);
        prop.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
        prop.setProperty("hibernate.current_session_context_class","thread");

        Configuration cfg = new Configuration()
                .addAnnotatedClass(User.class)
                .setProperties(prop);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties()).build();


       return cfg.buildSessionFactory(serviceRegistry);
    }


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public Statement getStatement(Connection connection) throws SQLException {
        return connection.createStatement();
    }
}
