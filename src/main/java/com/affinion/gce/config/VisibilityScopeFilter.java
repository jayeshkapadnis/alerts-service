package com.affinion.gce.config;

import com.affinion.gce.service.TenantAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class VisibilityScopeFilter implements WebFilter {

    private static final String VISIBILITY_SCOPE_HEADER = "X-Visibility-Scope-Key";

    private final TenantAuthService service;

    @Autowired
    public VisibilityScopeFilter(TenantAuthService service) {
        this.service = service;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (exchange.getRequest().getHeaders().containsKey(VISIBILITY_SCOPE_HEADER)) {
            List<String> visibilityScopeKeys = exchange.getRequest().getHeaders().get(VISIBILITY_SCOPE_HEADER);
            assert visibilityScopeKeys != null;
            return visibilityScopeKeys.stream().findFirst()
                    .filter(s -> !StringUtils.isEmpty(s)).map(key ->
                            service.detailsByScopeKey(key)
                                    .flatMap(s -> {
                                        exchange.getAttributes().put("clientBrmsKey", s);
                                        return chain.filter(exchange);
                                    })).orElse(chain.filter(exchange));
        }
        return chain.filter(exchange);
    }
}
