package com.pavllo.oauthtester.components;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.httpclient.HttpClient;
import com.github.scribejava.core.httpclient.HttpClientConfig;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.oauth2.clientauthentication.ClientAuthentication;
import com.github.scribejava.core.oauth2.clientauthentication.HttpBasicAuthenticationScheme;
import com.github.scribejava.core.oauth2.clientauthentication.RequestBodyAuthenticationScheme;

import java.io.OutputStream;

public class IdamApi20 extends DefaultApi20 {
    private IdamApi20() {
    }

    public static IdamApi20 instance() {
        return IdamApi20.InstanceHolder.INSTANCE;
    }

    public String getAccessTokenEndpoint() {
        return "https://localhost:8765/oauth/token?tenant=tenant1";
    }

    protected String getAuthorizationBaseUrl() {
        return "https://localhost:8765/oauth/authorize";
    }

    public ClientAuthentication getClientAuthentication() {
        return HttpBasicAuthenticationScheme.instance();
    }

    public OAuth20Service createService(String apiKey, String apiSecret, String callback, String defaultScope, String responseType, OutputStream debugStream, String userAgent, HttpClientConfig httpClientConfig, HttpClient httpClient) {
        return new OAuth20Service(this, apiKey, apiSecret, callback, defaultScope, responseType, debugStream, userAgent, httpClientConfig, httpClient);
    }

    private static class InstanceHolder {
        private static final IdamApi20 INSTANCE = new IdamApi20();

        private InstanceHolder() {
        }
    }
}
