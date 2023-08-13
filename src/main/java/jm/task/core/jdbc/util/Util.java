package jm.task.core.jdbc.util;

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


}
