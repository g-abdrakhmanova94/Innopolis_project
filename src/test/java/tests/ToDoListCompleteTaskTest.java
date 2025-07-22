package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.HttpCode;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ToDoListCompleteTaskTest {
    private static final String URL = "https://todo-app-sky.herokuapp.com/";
    private HttpClient client;
    private String taskId;

    @BeforeEach
    public void setup() throws IOException {
        client = HttpClientBuilder.create().build();
        taskId = findUnfinishedTaskId();
        if (taskId == null) {
            throw new RuntimeException("Нет невыполненных задач");
        }
    }

    private String findUnfinishedTaskId() throws IOException {
        String response = EntityUtils.toString(client.execute(new HttpGet(URL)).getEntity());
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> tasks = mapper.readValue(response, List.class);
        for (Map<String, Object> task : tasks) {
            Object completed = task.get("completed");
            if (completed == null || Boolean.FALSE.equals(completed)) {
                return String.valueOf(task.get("id"));
            }
        }
        return null;
    }

    @Test
    @DisplayName("Отметить задачу как выполненную")
    public void markTaskAsDone() throws IOException {
        HttpPatch patch = new HttpPatch(URL + taskId);
        String json = "{\"completed\": true}";
        patch.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        HttpResponse response = client.execute(patch);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode).isEqualTo(HttpCode.OK);
    }
}