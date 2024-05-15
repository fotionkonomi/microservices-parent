package al.run.apigateway.filter;

import al.run.apigateway.util.JwtUtil;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private WebClient.Builder builder;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                String jwtToken = authHeader;
                WebClient webClient = builder.build();

                return webClient
                        .post()
                        .uri("http://user-service/api/authentication/validate/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(jwtToken))
                        .retrieve()
                        .bodyToMono(Boolean.class)
                        .flatMap(response -> {
                            // Do further operations on microservice response if needed
                            exchange.getRequest().mutate().header("loggedInUser", jwtUtil.extractId(jwtToken)).build();
                            return chain.filter(exchange);
                        })
                        .onErrorResume(throwable -> {
                            throw new RuntimeException("un authorized access to application");
                        });
            } else {
                return chain.filter(exchange);
            }

        });

    }

    public static class Config {

    }
}
