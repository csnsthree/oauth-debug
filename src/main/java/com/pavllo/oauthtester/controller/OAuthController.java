package com.pavllo.oauthtester.controller;

import com.pavllo.oauthtester.model.OAuthRequestForm;
import com.pavllo.oauthtester.service.IdamOAuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
public class OAuthController {

    private IdamOAuthService idamOAuthService;

    @RequestMapping(value = "/authorize-url",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String getAuthorizeUrl(OAuthRequestForm oAuthRequestForm) {
        return idamOAuthService.getAuthorizationUrl(oAuthRequestForm);
    }

    @RequestMapping(value = "/callback",
            method = RequestMethod.GET)
    public String getAuthorizeUrl(@RequestParam String code, @RequestParam String state) throws InterruptedException, ExecutionException, IOException {
        return idamOAuthService.getUserData(code, state);
    }


}
