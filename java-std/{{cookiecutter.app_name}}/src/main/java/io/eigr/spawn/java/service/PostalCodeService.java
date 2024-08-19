package io.eigr.spawn.java.service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class PostalCodeService {
    private static final Logger log = LoggerFactory.getLogger(PostalCodeService.class);

    private final OkHttpClient client;

    public PostalCodeService() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .callTimeout(400, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .connectionPool(new ConnectionPool(256, 100, TimeUnit.SECONDS))
                .build();
    }

    public Map<String, String> find(String postalCode) {
        log.debug("Looking for Brazil PostalCode '{}'.", postalCode);
        // Massive use may block your access indefinitely.
        Request request = new Request.Builder()
                .url(String.format("https://viacep.com.br/ws/%s/json/", postalCode))
                .get()
                .build();

        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            assert response.body() != null;
            if(response.isSuccessful()) {
                String responseJson = response.body().string();
                Type type = new TypeToken<Map<String, String>>() { }.getType();
                Map<String, String> map = new Gson().fromJson(responseJson, type);
                log.debug("PostalCode response '{}'.", map);
                if(!map.containsKey("erro")) {
                    if(!map.containsKey("pais")) map.put("pais", "Brasil");
                    return map;
                }
            }
        } catch (Exception err) {
            log.error("Error while load postalcode.", err);
        }
        log.warn("postalcode '{}' not found.", postalCode);
        return new HashMap<>();
    }
}
