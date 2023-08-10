import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class FirstTry {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "root";

        return DriverManager.getConnection(url, username, password);
    }

    public static void createTable() {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();

            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(255) NOT NULL," +
                    "age INT)";

            statement.executeUpdate(createTableSQL);

            System.out.println("Таблица users успешно создана или уже существует.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        createTable();
//        try {
//            // Создайте таблицу, если её нет
//
//
//            // Попробуйте добавить нового пользователя
//            insertUser("John Doe", 30);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.err.println("Ошибка подключения к базе данных: " + e.getMessage());
//        }
    }

    // Метод insertUser остается без изменений
}

