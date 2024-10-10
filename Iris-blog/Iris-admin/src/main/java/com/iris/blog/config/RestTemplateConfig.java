package com.iris.blog.config;


import okhttp3.OkHttpClient;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author lstar
 * @create 2023-04
 * @description: RestTemplate配置
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        ClientHttpRequestFactory factory = httpRequestFactory();

        return new RestTemplate(factory);
    }

    public ClientHttpRequestFactory httpRequestFactory() {
        return new OkHttp3ClientHttpRequestFactory(okHttpConfigClient());
    }

    public OkHttpClient okHttpConfigClient() {
        int writeTimeout = 15;
        int readTimeout = 3;
        int connectTimeout = 3;
        return new OkHttpClient().newBuilder()
                        .connectTimeout(connectTimeout,TimeUnit.SECONDS)
                        .readTimeout(readTimeout,TimeUnit.SECONDS)
                        .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                        .build();

    }

}