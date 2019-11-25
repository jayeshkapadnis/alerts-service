package com.affinion.gce.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@Api(value = "Sample Controller for testing")
public class IndexController {

    private final WebClient webClient;

    public IndexController(@Qualifier("loadBalancedWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @ApiImplicitParam(name = "X-Visibility-Scope-Key",
            value = "Visibility scope key", required = true,
            paramType = "header", dataTypeClass = String.class)
    @GetMapping("/greeting")
    @ApiOperation(value = "Greeting for user.", authorizations = {@Authorization(value = "Bearer")})
    public Mono<String> hello(@ApiIgnore @RequestAttribute String clientBrmsKey) {
        log.info("Inside hello controller needing clientBrmsKey");
        return Mono.just("Hello " + clientBrmsKey);
    }

    @GetMapping("/greeting/without")
    @ApiOperation(value = "Greetings without visibility scope")
    public Mono<String> helloW() {
        log.info("Inside helloW controller");
        return Mono.just("Hello without key");
    }

    @GetMapping("/webclient")
    @ApiOperation(value = "WebClient call to test Keycloak service client_credentials")
    public Mono<Map<String, Object>> webClient() {
        log.info("Inside Webclient controller ********* ");
        return webClient.get()
                .uri("https://keycloak.dev.affinionservices.com/auth/realms/internal/protocol/openid-connect/userinfo")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(it -> it.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                }));
    }
}
