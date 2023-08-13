package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDaoJDBC = new UserDaoJDBCImpl();
        UserService userService = new UserServiceImpl(userDaoJDBC);
        //Создаем таблицу
        userService.createUsersTable();
        //добавляем 4-ех пользователей в таблицу
        for (byte i = 1; i < 5; i++) {
            userService.saveUser("name" + i, "lastName" + i, i);
            System.out.println("User с именем – name" + i + " добавлен в базу данных");
        }
        //Запршиваем всех пользователей из таблицы и выводим инфу о них
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        //Очищаем таблицу и удаляем ее
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
