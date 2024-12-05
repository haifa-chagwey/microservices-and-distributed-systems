package com.haifachagwey.apigateway.security;

public interface ApiKeyAuthorizationChecker {

    boolean isAuthorized(String api, String application) ;
}
