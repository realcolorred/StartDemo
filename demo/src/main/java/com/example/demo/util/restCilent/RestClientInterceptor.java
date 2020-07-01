package com.example.demo.util.restCilent;

import com.example.demo.util.CollectionHelper;
import com.example.demo.util.DateHelper;
import com.example.demo.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class RestClientInterceptor implements ClientHttpRequestInterceptor {

    private static Logger logger = LoggerFactory.getLogger(RestClientInterceptor.class);

    private String appMethodName;
    private String appId;
    private String appKey;

    void setAppMethodName(String appMethodName) {
        this.appMethodName = appMethodName;
    }

    void setAppId(String appId) {
        this.appId = appId;
    }

    void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().set(RestClientConst.APP_ID, appId);
        request.getHeaders().set(RestClientConst.APP_KEY, appKey);
        request.getHeaders().set(RestClientConst.REQUEST_ID, DateHelper.dateToString(new Date(), DateHelper.YYYYMMDDHHMMSSSSS));

        try {
            logRequest(request, body);
        } catch (Throwable ignored) {
        }

        long st = System.currentTimeMillis();
        ClientHttpResponse response = null;
        try {
            response = execution.execute(request, body);
        } finally {
            long elapsedTime = System.currentTimeMillis() - st;
            logResponse(request, response, elapsedTime);
        }
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) {
        String spanId = getSpandId(request);
        String methodTag = appMethodName;

        StringBuilder httpLog = new StringBuilder();
        httpLog.append(System.lineSeparator()).append(spanId)
                .append("|CS|").append(request.getMethod().name())
                .append("|").append(methodTag)
                .append("|").append(request.getURI().toString());

        HttpHeaders httpHeaders = request.getHeaders();
        if (httpHeaders != null) {
            for (String name : httpHeaders.keySet()) {
                List<String> values = httpHeaders.get(name);
                if (values != null) {
                    for (String value : values) {
                        httpLog.append(System.lineSeparator())
                                .append(spanId)
                                .append("|HEADER|").append(name)
                                .append("|").append(value);
                    }
                }
            }
        }

        if (body != null) {
            int bodyLength = body.length;
            if (bodyLength > 0) {
                String bodyText = new String(body);
                httpLog.append(System.lineSeparator()).append(spanId).append("|ARGUMENTS|").append(bodyLength).append("|").append(bodyText);
            }
        }

        logger.warn(httpLog.toString());
    }

    private void logResponse(HttpRequest request, ClientHttpResponse response, long elapsedTime) {
        String spanId = getSpandId(request);
        String methodTag = appMethodName;
        int status = 500;
        String reason = "unknown error.";
        if (response != null) {
            status = 200;
            reason = "OK";
            try {
                status = response.getStatusCode().value();
            } catch (IOException ignore) {
            }
        }

        try {
            if (status != 200) {
                try {
                    reason = response.getStatusText();
                } catch (Throwable ignored) {
                }
            }
        } finally {
            try {
                StringBuilder httpLog = new StringBuilder();
                httpLog.append(System.lineSeparator()).append(spanId)
                        .append("|CR|").append(request.getMethod().name())
                        .append("|").append(methodTag)
                        .append("|").append(request.getURI().getPath())
                        .append("|").append(elapsedTime)
                        .append("|").append(status)
                        .append("|").append(reason);
                logger.warn(httpLog.toString());
            } catch (Throwable ignored) {
            }
        }
    }

    private String getSpandId(HttpRequest request) {
        String spanId = CollectionHelper.getFirst(request.getHeaders().get(RestClientConst.REQUEST_ID));
        if (StringHelper.isEmpty(spanId)) {
            spanId = "";
        }
        return spanId;
    }
}
