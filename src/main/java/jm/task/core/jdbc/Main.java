package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        Util util = new Util();
        UserDao userDao = new UserDaoJDBCImpl(
                util.getStatement(util.getConnection())
        );
        UserDao userDao1 = new UserDaoHibernateImpl(util.getSessionFactory());
        //userDao1.cleanUsersTable();
        // userDao1.createUsersTable();
        //userDao.saveUser("Andr","Kor", (byte) 27);
        //  userDao.createUsersTable();
        //userDao.dropUsersTable();
        // String a = "Danil";
        //String b = "Sahnov";
        //byte c = 24;
        //userDao.saveUser(a, b, c);
        userDao.removeUserById(3);
        //userDao.getAllUsers();
        //userDao.cleanUsersTable();
        ;
    }

}
