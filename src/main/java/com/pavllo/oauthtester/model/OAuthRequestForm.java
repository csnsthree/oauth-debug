package com.pavllo.oauthtester.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthRequestForm {
    private String clientId;
    private String clientSecret;
    private String authorizeUrl;
    private String tokenUrl;
    private String resourceUrl;
    private String scopes;

}
