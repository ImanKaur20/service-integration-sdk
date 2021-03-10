package com.appdirect.sdk.web.oauth.model;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SessionRequestMatcher implements RequestMatcher {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic ";
    private static final String INTEGRATION_PATH = "/api/v2/integration/**";
    private static final String DOMAIN_ASSOCIATION_PATH = "/api/v1/domainassociation/**";
    private static final String MIGRATION_PATH = "/api/v1/migration/**";
    private static final String RESTRICTIONS_PATH = "/api/v1/restrictions/**";


    @Override
    public boolean matches(HttpServletRequest request) {
        return isAuthorizationBasicHeader(request) && antPathRequestMatcher(request);
    }

    private boolean isAuthorizationBasicHeader(HttpServletRequest request) {
        return null == request.getHeader(AUTHORIZATION) || request.getHeader(AUTHORIZATION).startsWith(BASIC);
    }

    private boolean antPathRequestMatcher(HttpServletRequest request) {
        return new AndRequestMatcher(
                new OrRequestMatcher(
                        new AntPathRequestMatcher(INTEGRATION_PATH),
                        new AntPathRequestMatcher(DOMAIN_ASSOCIATION_PATH),
                        new AntPathRequestMatcher(MIGRATION_PATH),
                        new AntPathRequestMatcher(RESTRICTIONS_PATH)
                )).matches(request);
    }
}
