package tests.contract;

import entities.User;
import helpers.HttpCode;
import helpers.UserHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteUserNameContractTest {

    private UserHelper userHelper;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() throws Exception {
        userHelper = new UserHelper();

        // Создаем объект пользователя
        User user = new User(123, "pp", "string", "string", "string",
                "string", "string", 123);
        // Сериализуем в JSON
        String userJson = new ObjectMapper().writeValueAsString(user);
        // Передаем сериализованный JSON в метод
        userHelper.createUser(userJson).close(); // создаем и закрываем ответ
    }

    @Test
    @DisplayName("Удаление существующего пользователя")
    public void deleteExistingUser_ShouldReturn200() throws Exception {
        Response response = userHelper.deleteUser("pp");
        String responseBody = response.body() != null ? response.body().string() : "";
        response.close();

        System.out.println("Статус код: " + response.code());
        System.out.println("Тело ответа: " + responseBody);

        // Проверка, что ответ с кодом 200
        assertThat(response.code()).isEqualTo(HttpCode.OK);

        // Проверка, что тело содержит сообщение с именем пользователя
        assertThat(responseBody).contains("\"message\":\"pp\"");
    }

    @Test
    @DisplayName("Удаление несуществующего пользователя")
    public void deleteNonExistingUserShouldHandleError() throws Exception {
        Response response = userHelper.deleteUser("LSL");
        String responseBody = response.body() != null ? response.body().string() : "";
        response.close();
        System.out.println("Статус код: " + response.code());
        System.out.println("Тело ответа: " + responseBody);
        assertThat(response.code()).isEqualTo(HttpCode.NOT_FOUND);
    }

    @Test
    @DisplayName("Отправка запроса на несуществующий URL")
    public void requestToInvalidUrlShouldReturnError() throws Exception {
        String invalidUrl = "https://petstore.swagger.io/v2/user/invalidendpoint";
        Response response = userHelper.sendRequestToUrl(invalidUrl);
        String responseBody = response.body() != null ? response.body().string() : "";
        System.out.println("Статус код: " + response.code());
        System.out.println("Тело ответа: " + responseBody);
        assertThat(response.code()).isEqualTo(HttpCode.NOT_FOUND);
        response.close();
    }
}