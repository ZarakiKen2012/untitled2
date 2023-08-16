package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {

    public static Connection getConnection() throws SQLException {
        //Создаем соеденение с БД, чтобы можно было выполнять запросы
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "root";
        return DriverManager.getConnection(url, username, password);
    }

    public static SessionFactory createSessionFactory() {
        //Создаем конфигурацию с нужными свойствами, указываем классы кот будут использованы в БД,
        //на основе настроенной конфигурации создаем объект SessionFactory для выполнения операция с БД
        try {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                    .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/mydatabase")
                    .setProperty("hibernate.connection.username", "root")
                    .setProperty("hibernate.connection.password", "root")
                    .addAnnotatedClass(User.class); // Здесь добавляем сущности
            return configuration.buildSessionFactory();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }
}
