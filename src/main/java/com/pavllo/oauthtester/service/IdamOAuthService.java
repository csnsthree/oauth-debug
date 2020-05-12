package com.pavllo.oauthtester.service;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.pavllo.oauthtester.components.GenericApi20;
import com.pavllo.oauthtester.model.OAuthRequestForm;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service
public class IdamOAuthService {

    public static final HashMap<String, OAuthRequestForm> secrets = new HashMap<>();

    public String getAuthorizationUrl(OAuthRequestForm oAuthRequestForm) {
        OAuth20Service service = new ServiceBuilder(oAuthRequestForm.getClientId())
                .apiSecret(oAuthRequestForm.getClientSecret())
                .defaultScope(oAuthRequestForm.getScopes())
                .callback("http://localhost:9091/callback")
                .build(GenericApi20.instance(oAuthRequestForm.getAuthorizeUrl(), oAuthRequestForm.getTokenUrl()));
        final String secretState = "secret" + new Random().nextInt(999_999);
        secrets.put(secretState, oAuthRequestForm);
        return service.createAuthorizationUrlBuilder()
                .state(secretState)
                .build();
    }

    public String getUserData(String code, String state) throws InterruptedException, ExecutionException, IOException {
        OAuthRequestForm oAuthRequestForm =secrets.get(state);
        OAuth20Service service = new ServiceBuilder(oAuthRequestForm.getClientId())
                .apiSecret(oAuthRequestForm.getClientSecret())
                .defaultScope(oAuthRequestForm.getScopes())
                .callback("https://oauth-debug.herokuapp.com/callback")
                .build(GenericApi20.instance(oAuthRequestForm.getAuthorizeUrl(), oAuthRequestForm.getTokenUrl()));
        OAuth2AccessToken accessToken = service.getAccessToken(code);
        accessToken = service.refreshAccessToken(accessToken.getRefreshToken());
        final OAuthRequest request = new OAuthRequest(Verb.GET, oAuthRequestForm.getResourceUrl());
        service.signRequest(accessToken, request);
        System.out.println();
        try (Response response = service.execute(request)) {
            System.out.println(response.getCode());
            System.out.println(response.getBody());
            return response.getBody();
        }
    }
}
