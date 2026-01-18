package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {


    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS users (
            id BIGINT PRIMARY KEY AUTO_INCREMENT,
            name VARCHAR(50),
            lastName VARCHAR(50),
            age TINYINT
        )
        """;

        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.createNativeQuery(sql).executeUpdate();
        tx.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Session session = getSessionFactory().openSession();) {
            Transaction tx = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            tx.commit();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = getSessionFactory().openSession();) {
            Transaction tx = session.beginTransaction();

            User user = new User(name, lastName, age);
            session.save(user);

            tx.commit();
        }

    }

    @Override
    public void removeUserById(long id) {

        try (Session session = getSessionFactory().openSession();) {
            Transaction tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }

            tx.commit();
        }

    }

    @Override
    public List<User> getAllUsers() {


        try (Session session = getSessionFactory().openSession();) {
            List<User> users = session.createQuery("FROM User", User.class).getResultList();
            return users;
        }

    }

    @Override
    public void cleanUsersTable() {

        try (Session session = getSessionFactory().openSession();) {
            Transaction tx = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE users").executeUpdate();

            tx.commit();
        }

    }
}
