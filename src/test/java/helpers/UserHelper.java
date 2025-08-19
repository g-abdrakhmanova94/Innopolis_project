package helpers;

import okhttp3.*;

import java.io.IOException;

public class UserHelper {
    private static final String URL = "https://petstore.swagger.io/v2/user/";
    private final OkHttpClient client = new OkHttpClient();

    public Response updateUserWithQueryParam(String username, String jsonBody) throws IOException {
        HttpUrl url = HttpUrl.parse(URL + username).newBuilder().build();
        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .put(body)
                .build();
        return client.newCall(request).execute();
    }

    public Response createUser(String jsonBody) throws IOException {
        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(URL)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        return client.newCall(request).execute();
    }

    public Response deleteUser(String username) throws IOException {
        Request request = new Request.Builder()
                .url(URL + username)
                .addHeader("accept", "application/json")
                .delete()
                .build();
        return client.newCall(request).execute();
    }

    public Response updateUserWithCustomUrl(String url, String username, String jsonBody) throws IOException {
        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url + "/" + username)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .put(body)
                .build();
        return client.newCall(request).execute();
    }

    public Response sendRequestToUrl(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("accept", "application/json")
                .delete()
                .build();
        return client.newCall(request).execute();
    }
}