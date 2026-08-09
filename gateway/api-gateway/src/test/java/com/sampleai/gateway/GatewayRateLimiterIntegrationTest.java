package com.sampleai.gateway;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.cloud.gateway.routes[0].id=product-route",
        "spring.cloud.gateway.routes[0].predicates[0]=Path=/api/test/**",
        "spring.cloud.gateway.routes[0].filters[0]=RequestRateLimiter=\n",
})
public class GatewayRateLimiterIntegrationTest {
    private static MockWebServer mockWebServer;

    @LocalServerPort
    int port;

    @Autowired
    private WebTestClient webClient;

    @BeforeAll
    public static void startServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    public static void stopServer() throws IOException {
        if (mockWebServer != null) mockWebServer.shutdown();
    }

    @Test
    public void rateLimiterAllocsAllowThenDeny() throws Exception {
        // enqueue backend response
        mockWebServer.enqueue(new MockResponse().setBody("OK").setResponseCode(200));
        mockWebServer.enqueue(new MockResponse().setBody("OK").setResponseCode(200));

        String backendUrl = mockWebServer.url("/").toString();

        // Set route uri to mock server dynamically
        System.setProperty("spring.cloud.gateway.routes[0].uri", backendUrl);

        // First request should be allowed by our test RateLimiter
        webClient.post().uri("http://localhost:" + port + "/api/test/1").exchange().expectStatus().is2xxSuccessful();
        // Second request should be allowed as well (RateLimiter permits 2)
        webClient.post().uri("http://localhost:" + port + "/api/test/2").exchange().expectStatus().is2xxSuccessful();
        // Third request should be rate-limited (429)
        webClient.post().uri("http://localhost:" + port + "/api/test/3").exchange().expectStatus().isEqualTo(429);
    }

    @Configuration
    static class TestConfig {
        @Bean
        public RateLimiter<Object> testRateLimiter() {
            return new RateLimiter<Object>() {
                final AtomicInteger counter = new AtomicInteger(0);

                @Override
                public Mono<Response> isAllowed(String routeId, String id) {
                    int c = counter.incrementAndGet();
                    boolean allowed = c <= 2;
                    return Mono.just(new Response(allowed, Map.of()));
                }
            };
        }
    }
}
