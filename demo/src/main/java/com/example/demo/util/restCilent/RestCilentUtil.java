package com.example.demo.util.restCilent;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RestCilentUtil {

    private static Map<String, ClientHttpRequestFactory> mapFactory = new ConcurrentHashMap<>();

    public static RestTemplate getRestCilent(String appMethodName) {
        ClientHttpRequestFactory factory = getClientHttpRequestFactory(10000, 60000);
        RestTemplate restTemplate = new RestTemplate(factory);
        RestClientInterceptor interceptor = new RestClientInterceptor();
        interceptor.setAppMethodName(appMethodName);
        interceptor.setAppId("123");
        interceptor.setAppKey("123");
        restTemplate.getInterceptors().add(interceptor);
        return restTemplate;
    }

    private static ClientHttpRequestFactory getClientHttpRequestFactory(int connectTimeout, int soTimeout) {
        String key = connectTimeout + "_" + soTimeout;
        ClientHttpRequestFactory factory = mapFactory.get(key);
        if (factory == null) {
            synchronized (mapFactory) {
                factory = mapFactory.get(key);
                if (factory == null) {
                    RequestConfig config = RequestConfig.custom()
                            .setConnectionRequestTimeout(connectTimeout)
                            .setConnectTimeout(connectTimeout)
                            .setSocketTimeout(soTimeout)
                            .build();
                    HttpClientBuilder builder = HttpClientBuilder.create()
                            .setDefaultRequestConfig(config)
                            .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
                    HttpClient httpClient = builder.build();
                    factory = new HttpComponentsClientHttpRequestFactory(httpClient);
                    mapFactory.put(key, factory);
                }
            }
        }
        return factory;
    }
}
