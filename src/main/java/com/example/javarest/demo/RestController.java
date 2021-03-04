package com.example.javarest.demo;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    @PostMapping("inn")
    public String processInn(@org.springframework.web.bind.annotation.RequestBody ProcessInnDto req) throws IOException {
        RequestBody body = RequestBody.create(JSON, String.format("{ \"query\": \"%s\" }", req.inn));
        Request request = new Request.Builder()
                .url("https://suggestions.dadata.ru/suggestions/api/4_1/rs/findById/party")
                .post(body)
                .addHeader("Authorization", String.format("Token %s", "2ef4f07af5f6e8d44e260cdd79c676f98419be10"))
                .build();
        Response resp = client.newCall(request).execute();
        assert resp.body() != null;

        JSONObject respJson = new JSONObject(resp.body().string());
        JSONArray suggestions = respJson.getJSONArray("suggestions");
        List<String> addresses = new ArrayList();
        for (int i = 0; i < suggestions.length(); i++)
        {
            JSONObject data = suggestions.getJSONObject(i).getJSONObject("data");
            JSONObject address = data.getJSONObject("address");
            addresses.add(address.getString("value"));
        }

        return "respBody";
    }
}
