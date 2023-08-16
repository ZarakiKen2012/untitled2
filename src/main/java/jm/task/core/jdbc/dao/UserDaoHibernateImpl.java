package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.createSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(50) NOT NULL," +
                "lastname VARCHAR(50) NOT NULL," +
                "age TINYINT NOT NULL)";
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();//Начало транзакции, либо выполнится все и успешно, либо откат
            session.createSQLQuery(createTableSQL).executeUpdate();
            transaction.commit();//Фиксируем успешное завершение транзакции
        } catch (Exception e) {
            if (transaction == null) {//вдруг исплючение не связано с транзакцией
                transaction.rollback();//Откатываем транзакцию в случае ошибки
            }
            System.out.println(e);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        String createTableSQL = "DROP TABLE IF EXISTS users";
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(createTableSQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction == null) {
                transaction.rollback();
            }
            System.out.println(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction == null) {
                transaction.rollback();
            }
            System.out.println(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            } else {
                System.out.println("Пользователь с ID " + id + " не найден.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction == null) {
                transaction.rollback();
            }
            System.out.println(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = sessionFactory.openSession()) {
            //Создаем объект Query представляющий собой запрос к БД на языке HQL
            Query<User> query = session.createQuery("FROM User", User.class);
            users = query.getResultList();
        } catch (Exception e) {
            System.out.println(e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        String createTableSQL = "DELETE FROM users";
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(createTableSQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction == null) {
                transaction.rollback();
            }
            System.out.println(e);
        }
    }
}
