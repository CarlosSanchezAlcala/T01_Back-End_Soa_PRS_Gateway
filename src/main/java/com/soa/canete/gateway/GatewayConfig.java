package com.soa.canete.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AddRequestHeaderGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route((r) -> r.path("/api/funcionaryData/**")
                        .uri("http://localhost:8081"))

                .route((r) -> r.path("/api/teenData/**")
                        .uri("http://localhost:8082"))

                .route((r) -> r.path("/api/address/**")
                        .uri("http://localhost:8083"))

                .route((r) -> r.path("/api/attorneyData/**")
                        .uri("http://localhost:8084"))

                .route((r) -> r.path("/api/operativeUnit/**")
                        .uri("http://localhost:8085"))

                .route((r) -> r.path("/api/transferTeen/**")
                        .uri("http://localhost:8086"))

                .route((r) -> r.path("/getData")
                        .uri("http://localhost:8087"))

                .route((r) -> r.path("/api/transaccionalData/**")
                        .uri("http://localhost:8088"))

                .route((r) -> r.path("/api/activitiesData/**")
                        .uri("http://localhost:8089"))

                .route((r) -> r.path("/api/transactionalDataSimple/**")
                        .uri("http://localhost:8090"))

                .route((r) -> r.path("/api/attendance/**")
                        .uri("http://localhost:8091"))

                .route((r) -> r.path("/api/programs/**")
                        .uri("http://localhost:8092"))

                .build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("*");
        corsConfig.addAllowedMethod(HttpMethod.GET);
        corsConfig.addAllowedMethod(HttpMethod.POST);
        corsConfig.addAllowedMethod(HttpMethod.DELETE);
        corsConfig.addAllowedMethod(HttpMethod.PUT);
        corsConfig.addAllowedMethod(HttpMethod.PATCH);
        corsConfig.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }

    private GatewayFilter addRequestHeader(String headerName, String headerValue) {
        return (exchange, chain) -> {
            // Agrega logs de depuración
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            System.out.println("access_token: " + token);

            // Aplica la lógica original para agregar el encabezado
            return chain.filter(exchange.mutate().request(exchange.getRequest().mutate().header(headerName, headerValue).build()).build());
        };
    }

}
