package ru.otus.java.pro.mt.core.transfers.integrations;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.stereotype.Component;
import ru.otus.java.pro.mt.core.transfers.configs.properties.RestClientProperties;


@Component
public class RestClientFactory {

    public OkHttpClient createClient(RestClientProperties props) {
        return new OkHttpClient.Builder()
                .connectTimeout(props.getReadTimeout())
                .readTimeout(props.getReadTimeout())
                .writeTimeout(props.getWriteTimeout())
                .build();
    }

    public Request.Builder createRequestBuilder(RestClientProperties props) {
        return new Request.Builder().url(props.getUrl());
    }
}
