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

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteUserNameBusinessTest {
    private static final String URL = "https://petstore.swagger.io/";
    private OkHttpClient httpClient;
    private ObjectMapper objectMapper;
    private UserHelper userHelper;
    private String username;

    @BeforeEach
    public void setUp() {
        httpClient = new OkHttpClient();
        objectMapper = new ObjectMapper();
        userHelper = new UserHelper();
        username = "Mary";
    }

    @Test
    public void createAndDeleteUser() throws IOException {
        // Создаём пользователя
        User user = new User(null, "Mary", "string", "string", "string",
                "string", "string", null);
        String jsonBody = objectMapper.writeValueAsString(user);
        Response createResponse = userHelper.createUser(jsonBody);
        int createStatus = createResponse.code();
        createResponse.close();
        System.out.println("Создание пользователя статус: " + createStatus);
        assertThat(createStatus).isBetween(200, 299);

        String getUrl = URL + "v2/user/" + username;

        // Проверка, что пользователь есть
        Response getRes = httpClient.newCall(new Request.Builder()
                .url(getUrl)
                .get()
                .addHeader("accept", "application/json")
                .build()).execute();
        int getStatus = getRes.code();
        getRes.close();
        System.out.println("Получение пользователя перед удалением статус: " + getStatus);
        assertThat(getStatus).isEqualTo(HttpCode.OK);

        // Удаление пользователя
        Response deleteRes = userHelper.deleteUser(username);
        int deleteStatus = deleteRes.code();
        deleteRes.close();
        System.out.println("Удаление пользователя статус: " + deleteStatus);
        assertThat(deleteStatus).isEqualTo(HttpCode.OK);

        // Проверка, что пользователь удален
        // ВНИМАНИЕ: здесь обнаружен баг: удаляется пользователь, но при вызове его методом гет периодически выходит
        // статус код 200, а не 404
        Response getResAfterDel = httpClient.newCall(new Request.Builder()
                .url(getUrl)
                .get()
                .addHeader("accept", "application/json")
                .build()).execute();
        int getStatusAfterDel = getResAfterDel.code();
        getResAfterDel.close();
        System.out.println("Проверка после удаления статус: " + getStatusAfterDel);
        assertThat(getStatusAfterDel).isEqualTo(HttpCode.NOT_FOUND);
    }
}