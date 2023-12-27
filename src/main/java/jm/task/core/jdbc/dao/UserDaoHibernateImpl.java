package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;
     private Util util;

    public UserDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void createUsersTable() {
        sessionFactory = util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("""
                create table if not exists users(
                    id  serial primary key,
                    name varchar(200) not null,
                    lastName varchar(200) not null,
                    age int not null
                    );
                """).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        sessionFactory = util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("""
                drop table userr;
                """).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        sessionFactory = util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        sessionFactory = util.getSessionFactory();
        try {
            User user = new User();
            long userId = user.getId(id);
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            User users = session.get(User.class, userId);
            session.getTransaction().commit();
            System.out.println(user);
        } catch (HibernateException e) {
            System.out.println(e);
        }



    }

    @Override
    public List<User> getAllUsers() {
        Util util = new Util();
        sessionFactory = util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<User> users = session.createQuery("from User")
                .getResultList();
        session.getTransaction().commit();
        session.close();
        System.out.println(users);
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Util util = new Util();
        sessionFactory = util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete from User")
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
