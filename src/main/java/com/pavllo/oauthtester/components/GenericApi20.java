package com.pavllo.oauthtester.components;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.oauth2.clientauthentication.ClientAuthentication;
import com.github.scribejava.core.oauth2.clientauthentication.HttpBasicAuthenticationScheme;
import com.github.scribejava.core.oauth2.clientauthentication.RequestBodyAuthenticationScheme;

public class GenericApi20 extends DefaultApi20 {
    private GenericApi20() {
    }
    private static String authorizeUrl;
    private static String tokenUrl;
    public static GenericApi20 instance(String authorizeUrl,String tokenUrl) {
        GenericApi20.authorizeUrl=authorizeUrl;
        GenericApi20.tokenUrl=tokenUrl;
        return GenericApi20.InstanceHolder.INSTANCE;
    }

    public String getAccessTokenEndpoint() {
        return GenericApi20.tokenUrl;
    }

    protected String getAuthorizationBaseUrl() {
        return  GenericApi20.authorizeUrl;
    }

    public ClientAuthentication getClientAuthentication() {
        return HttpBasicAuthenticationScheme.instance();
    }

    private static class InstanceHolder {
        private static final GenericApi20 INSTANCE = new GenericApi20();

        private InstanceHolder() {
        }
    }
}
