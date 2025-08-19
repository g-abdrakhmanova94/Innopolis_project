package tests.business;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.User;
import helpers.HttpCode;
import helpers.UserHelper;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserBusinessTest {
    private static final String URL = "https://petstore.swagger.io/";
    private OkHttpClient httpClient;
    private ObjectMapper objectMapper;
    private UserHelper userHelper;
    private String username; // постоянное значение
    private int createdUserId;

    @BeforeEach
    public void setUp() {
        httpClient = new OkHttpClient();
        objectMapper = new ObjectMapper();
        userHelper = new UserHelper();

        // Устанавливаем фиксированный username
        username = "Lori";
    }

    @Test
    public void createUserAndVerifyExists() throws IOException {
        // Создаем объект User без id и userStatus
        User user = new User(null, "Lori", "string", "string", "string",
                "string", "string", null);

        String jsonBody = objectMapper.writeValueAsString(user);

        // Создаем пользователя
        Response createResponse = userHelper.createUser(jsonBody);
        System.out.println("Create response status: " + createResponse.code());
        String createBodyStr = createResponse.body().string(); // читаем тело один раз
        System.out.println("Create response body: " + createBodyStr);
        assertThat(createResponse.code()).isEqualTo(HttpCode.OK);
        createResponse.close();

        // Выполняем GET-запрос для проверки, что наш созданный пользователь есть
        String url = URL + "v2/user/" + username;
        Request getRequest = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .build();

        Response getResponse = httpClient.newCall(getRequest).execute();
        System.out.println("GET response status: " + getResponse.code());
        String getBodyStr = getResponse.body().string(); // читаем тело один раз
        System.out.println("GET response body: " + getBodyStr);
        assertThat(getResponse.code()).isEqualTo(HttpCode.OK);
        JsonNode userJson = objectMapper.readTree(getBodyStr);
        assertThat(userJson.get("username").asText()).isEqualTo(username);
        getResponse.close();

        // Всегда удаляем пользователя - постусловие
        Response deleteResponse = userHelper.deleteUser(username);
        System.out.println("Delete response status: " + deleteResponse.code());
        String deleteBody = deleteResponse.body().string();
        System.out.println("Delete response body: " + deleteBody);
        deleteResponse.close();
    }
}