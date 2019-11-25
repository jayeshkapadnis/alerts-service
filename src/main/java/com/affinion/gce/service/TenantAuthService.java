package com.affinion.gce.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class TenantAuthService {

    /*private final TenantRepository repository;

    @Autowired
    public TenantAuthService(TenantRepository repository) {
        this.repository = repository;
    }*/

    public Mono<String> detailsByScopeKey(String key) {
        log.info("*********** Key in service ****** {}", key);
        return Mono.just(key);//repository.findById(Long.parseLong(key));
    }
}
