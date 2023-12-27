package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.management.Query;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final Statement statement;


    public UserDaoJDBCImpl(Statement statement) {

        this.statement = statement;
    }

    public void createUsersTable() {
        try {
            statement.executeUpdate("""
                                    
                    create table if not exists users(
                    id   serial primary key,
                    name varchar(200) not null,
                    lastName varchar(200) not null,
                    age int not null
                    );""");
        } catch (SQLException e) {
            log.info(e.getSQLState());
        }
    }

    public void dropUsersTable() {
        try {
            statement.executeUpdate("""
                    drop table userr;
                    """);
        } catch (SQLException e) {
            log.info(e.getSQLState());
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {

        Util util = new Util();
        Connection connection = util.getConnection();
        String save = "insert into userr (name, lastName, age) values( ?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(save);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, lastName);
        preparedStatement.setByte(3, age);
        int resultSet = preparedStatement.executeUpdate();


    }


    public void removeUserById(long id) throws SQLException {
        Util util = new Util();
        Connection connection = util.getConnection();
        String s = "select * from userr where id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(s);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong(1));
            user.setName(resultSet.getString(2));
            user.setLastName(resultSet.getString(3));
            user.setAge(resultSet.getByte(4));
            System.out.println(user);

        }


    }

    public List<User> getAllUsers() throws SQLException {
        Util util = new Util();
        Connection connection = util.getConnection();
        String s = "select name, lastName, age from userr;";
        PreparedStatement preparedStatement = connection.prepareStatement(s);
        ResultSet resultSet = preparedStatement.executeQuery();


        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(new User(resultSet.getString(1), resultSet.getString(2), resultSet.getByte(3)));
        }
        System.out.println(users);
        return users;
    }


    public void cleanUsersTable() throws SQLException {
        statement.executeUpdate("""
                  truncate  userr;
                """);

    }
}
