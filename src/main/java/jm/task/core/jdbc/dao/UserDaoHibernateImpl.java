package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    Session session = Util.getSessionFactory().openSession();

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

        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.createNativeQuery(sql).executeUpdate();
        tx.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {


        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        User user = new User(name, lastName, age);
        session.save(user);

        tx.commit();
        session.close();

    }

    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        User user = session.get(User.class, id);
        if (user != null) {
            session.delete(user);
        }
        session.delete(user);

        tx.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = getSessionFactory().openSession();
        List<User> users = session.createQuery("FROM User", User.class).getResultList();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.createNativeQuery("TRUNCATE TABLE users").executeUpdate();

        tx.commit();
        session.close();
    }
}
