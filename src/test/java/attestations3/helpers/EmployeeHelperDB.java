package attestations3.helpers;

import attestations3.entities.EmployeeRequest;
import attestations3.entities.EmployeeResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static io.restassured.RestAssured.given;

public class EmployeeHelperDB extends AbstractHelper {
    /**
     * Класс для работы с базой данных сотрудников, наследующий от AbstractHelper.
     * Методы включают:
     * - Конструктор для установки соединения с базой данных.
     * - postEmployee: добавляет нового сотрудника и возвращает его данные.
     * - getEmployee: получает сотрудника по ID и возвращает его данные.
     * - deleteEmployee: удаляет сотрудника по ID через HTTP запрос.
     */

    public EmployeeHelperDB() throws SQLException {
        connection = getConnection();
    }

    public EmployeeResponse postEmployee(EmployeeRequest employee) throws SQLException {
        String INSERT_EMPLOYEE = "INSERT INTO employee(\"name\", \"surname\", \"city\", \"position\") values (?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, employee.getName());
        preparedStatement.setString(2, employee.getSurname());
        preparedStatement.setString(3, employee.getCity());
        preparedStatement.setString(4, employee.getPosition());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            return new EmployeeResponse(employee.getCity(), employee.getName(), employee.getPosition(), employee.getSurname(), id);
        } else {
            return null; // Обработка ошибок
        }
    }

    public EmployeeResponse getEmployee(int id) throws SQLException {
        String SELECT_NAME_SURNAME = "SELECT * FROM employee WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NAME_SURNAME);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new EmployeeResponse(
                    resultSet.getString("city"),
                    resultSet.getString("name"),
                    resultSet.getString("position"),
                    resultSet.getString("surname"),
                    resultSet.getInt("id")
            );
        } else {
            return null; // Возвращаем null, если сотрудник не найден
        }
    }

    public void deleteEmployee(int id) {
        given().
                when().
                delete("/employee/" + id);
    }
}
