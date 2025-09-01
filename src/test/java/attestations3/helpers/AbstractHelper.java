package attestations3.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract public class AbstractHelper {
    /**
     * Абстрактный класс для работы с базой данных.
     * Основные характеристики:
     * - Поле connection для хранения соединения с базой данных.
     * - Метод getConnection: устанавливает и возвращает соединение с PostgreSQL.
     */
    protected Connection connection;

    public Connection getConnection() throws SQLException {
        String connectionString = "jdbc:postgresql://dpg-d2o8fqjipnbc73begg4g-a.oregon-postgres.render.com/employee_postgres_2";
        String username = "admin";
        String password = "azaHlBwfeeGxwEUt07mK6xtGrwBC1DCK";
        return DriverManager.getConnection(connectionString, username, password);
    }
}

