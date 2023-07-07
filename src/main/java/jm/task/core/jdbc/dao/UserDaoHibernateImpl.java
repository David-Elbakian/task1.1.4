package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory factory = Util.getFactoryConnection();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try  {
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id SERIAL PRIMARY KEY, name VARCHAR(50), lastname VARCHAR(50), age INTEGER)").executeUpdate();
            transaction.commit();
            System.out.println("Table is created");
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createNativeQuery("Drop table if exists users").executeUpdate();
            transaction.commit();
            System.out.println("Table is deleted");
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = factory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.println("User is deleted");
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Query<User> query = session.createNativeQuery("SELECT id, name, lastName, age from users", User.class);
            userList = query.list();
            transaction.commit();
            return userList;
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            String sql = "SELECT * FROM user1";
            session.createNativeQuery("DELETE FROM users").executeUpdate();
            transaction.commit();
            System.out.println("Table is clean");
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
