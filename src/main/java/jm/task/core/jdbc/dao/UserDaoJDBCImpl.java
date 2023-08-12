package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl implements UserDao {

    public void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(50) NOT NULL," +
                "lastname VARCHAR(50) NOT NULL," +
                "age TINYINT)";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
            System.out.println("Таблица users успешно создана.");
        } catch (SQLException e) {
            System.out.println("Ошибка при соеденении с таблицей или при создании таблицы\n" + e);
        }
    }

    public void dropUsersTable() {
        String dropUsersTableSQL = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropUsersTableSQL);
            System.out.println("Таблица users успешно удалена.");
        } catch (SQLException e) {
            System.out.println("Ошибка при соеденении с таблицей или при удалении таблицы\n" + e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUserSQL = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection();//Создали соеденение с бд
             PreparedStatement preparedStatement = connection.prepareStatement(saveUserSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Пользователь успешно добавлен!");
            } else {
                System.out.println("Не удалось добавить пользователя.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при соеденении с таблицей или при добавлении юзера\n" + e);
        }
    }

    public void removeUserById(long id) {
        String removeUserByIdSQL = "DELETE FROM users WHERE id = ?";//Создаем запрос, где для каждого ? на передать значение по его номеру (1,2,3...)
        try (Connection connection = Util.getConnection();//Создали соеденение с бд
             PreparedStatement preparedStatement = connection.prepareStatement(removeUserByIdSQL)) {//создали объект, с нашим sql запросом с параметрами
            preparedStatement.setLong(1, id);//Устанавливаем значние 1-му знаку ? в нашем запросе (setЧто-то)
            //Применяем к предворительному запросу метод executeUpdate() кот применяется для выполнения запросов изменяющих данные в БД
            int rowsDeleted = preparedStatement.executeUpdate();//возвращает кол-во измененных строк
            if (rowsDeleted > 0) {
                System.out.println("Пользователь успешно удален!");
            } else {
                System.out.println("Пользователь с указанным ID не найден.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при соеденении с таблицей или при удалении пользователя по ID\n" + e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectAllUsersSQL = "SELECT * FROM users";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectAllUsersSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastname");
                byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при соеденении с таблицей или при получении всех пользователей из таблицы\n" + e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        String cleanUsersTableSQL = "DELETE FROM users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(cleanUsersTableSQL);
            System.out.println("Таблица users успешно очищена.");
        } catch (SQLException e) {
            System.out.println("Ошибка при соеденении с таблицей или при очистке таблицы\n" + e);
        }
    }
}
