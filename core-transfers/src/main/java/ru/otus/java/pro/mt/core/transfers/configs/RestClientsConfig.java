package ru.otus.java.pro.mt.core.transfers.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import okhttp3.OkHttpClient;
import org.springframework.web.client.RestClient;
import ru.otus.java.pro.mt.core.transfers.configs.properties.RestClientProperties;
import ru.otus.java.pro.mt.core.transfers.integrations.RestClientFactory;

@Configuration
public class RestClientsConfig {
    @Bean
    public OkHttpClient okHttpClient(RestClientFactory factory, RestClientProperties props) {
        return factory.createClient(props);
    }

    @Bean
    public RestClient restClient(RestClientProperties props) {
        return RestClient.builder()
                .baseUrl(props.getUrl())
                .build();
    }

    @Bean
    public RestClientProperties restClientProperties() {
        return new RestClientProperties();
    }
}

