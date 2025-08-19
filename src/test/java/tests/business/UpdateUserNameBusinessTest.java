package tests.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.User;
import helpers.HttpCode;
import helpers.UserHelper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateUserNameBusinessTest {
    private static final String URL = "https://petstore.swagger.io/v2/user/";
    private final UserHelper userHelper = new UserHelper();
    private final ObjectMapper mapper = new ObjectMapper();
    private final OkHttpClient httpClient = new OkHttpClient();
    private String oldUsername = "Mary";
    private String newUsername = "Ben";

    @BeforeEach
    public void setUp() throws Exception {
        // Создаем пользователя "Mary" перед каждым тестом
        User user = new User(null, oldUsername, "string", "string", "string", "string", "string", null);
        try (Response respCreate = userHelper.createUser(mapper.writeValueAsString(user))) {
            System.out.println("Create статус: " + respCreate.code());
            assertThat(respCreate.code()).isEqualTo(HttpCode.OK);
        }
    }

    @Test
    public void createUpdateGetDeleteUser() throws Exception {
        // Обновляем пользователя — меняем username на "Ben"
        User updatedUser = new User(null, newUsername, "updatedFirst", "updatedLast", "updatedEmail", "updatedPass", "updatedPhone", null);
        try (Response respUpdate = userHelper.updateUserWithQueryParam(oldUsername, mapper.writeValueAsString(updatedUser))) {
            System.out.println("Update статус: " + respUpdate.code());
            assertThat(respUpdate.code()).isEqualTo(HttpCode.OK);
        }

        // Получаем обновленного пользователя по новому username
        try (Response respGet = httpClient.newCall(
                new Request.Builder()
                        .url(URL + newUsername)
                        .addHeader("accept", "application/json")
                        .build()
        ).execute()) {
            System.out.println("Get статус: " + respGet.code());
            String body = respGet.body().string();
            System.out.println("Информация о пользователе: " + body);
            assertThat(respGet.code()).isEqualTo(HttpCode.OK);
            assertThat(body).contains("\"username\":\"Ben\"")
                    .contains("\"firstName\":\"updatedFirst\"")
                    .contains("\"lastName\":\"updatedLast\"");
        }

        // Удаляем пользователя
        try (Response respDelete = userHelper.deleteUser(newUsername)) {
            System.out.println("Удаление статус: " + respDelete.code());
            assertThat(respDelete.code()).isEqualTo(HttpCode.OK);
        }

        // Проверяем, что пользователь удален
        try (Response respCheck = httpClient.newCall(
                new Request.Builder()
                        .url(URL + newUsername)
                        .addHeader("accept", "application/json")
                        .build()
        ).execute()) {
            System.out.println("Проверка после удаления статус: " + respCheck.code());
            // Обычно API возвращает 404 для несуществующего пользователя
            // ВНИМАНИЕ: здесь обнаружен баг: удаляется пользователь, но при вызове его методом гет периодически выходит
            // статус код 200, а не 404
            assertThat(respCheck.code()).isEqualTo(HttpCode.NOT_FOUND);
        }
    }
}