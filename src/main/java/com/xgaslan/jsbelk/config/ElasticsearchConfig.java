package com.xgaslan.jsbelk.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.support.HttpHeaders;
import org.springframework.lang.NonNullApi;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

@Slf4j
@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .usingSsl(createSSLContext())
                .withHeaders(() -> {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "ApiKey SVIwaWZKa0JWOGNLdEtseWlvd1E6WU1oVFNrZEZRTzY3dkdKSjUxc1RxZw==");
            return headers;
        })
                .build();
    }

    private SSLContext createSSLContext() {
        try {
            // Create a trust manager that trusts all certificates (for development only)
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[0];
                        }
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        }
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return sslContext;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create SSL context", e);
        }
    }
}
