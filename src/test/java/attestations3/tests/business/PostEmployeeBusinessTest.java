package attestations3.tests.business;

import attestations3.entities.EmployeeRequest;
import attestations3.entities.EmployeeResponse;
import attestations3.helpers.EmployeeHelper;
import attestations3.helpers.EmployeeHelperDB;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.SQLException;

import static io.restassured.RestAssured.baseURI;

public class PostEmployeeBusinessTest {
    /**
     * Класс тестирования создания сотрудников через API.
     * Методы включают:
     * - Проверку успешного создания сотрудника и соответствия ID в ответе.
     * - Проверку создания сотрудника с пустым телом запроса, ожидая -1 в качестве ID и null в качестве ответа.
     */
    private static EmployeeHelper employeeHelper;
    private static EmployeeHelperDB employeeHelperDB;

    @BeforeAll
    public static void setUri() throws SQLException {
        baseURI = "https://innopolispython.onrender.com";
        employeeHelper = new EmployeeHelper();
        employeeHelperDB = new EmployeeHelperDB();
    }

    @Test
    @DisplayName("Создание сотрудника")
    public void postEmployee() throws Exception {
        int employeeId = employeeHelper.postEmployee(new EmployeeRequest("Kazan", "Mary", "AQA", "Addington"));
        EmployeeResponse employee = employeeHelperDB.getEmployee(employeeId);
        assertEquals(employeeId, employee.getId());
    }

    @Test
    @DisplayName("Создание сотрудника с пустым полем")
    public void createEmployeeWithEmptyBody() throws Exception {
        int employeeId = employeeHelper.postEmployee(new EmployeeRequest());
        EmployeeResponse employee = employeeHelperDB.getEmployee(employeeId);

        assertEquals(-1, employeeId);
        assertNull(employee); // Проверяем, что employee равен null
    }
}

