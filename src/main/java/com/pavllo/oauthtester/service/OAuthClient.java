//package com.pavllo.oauthtester.service;
//
//import com.github.scribejava.core.builder.ServiceBuilder;
//import com.github.scribejava.core.model.OAuth2AccessToken;
//import com.github.scribejava.core.model.OAuthRequest;
//import com.github.scribejava.core.model.Response;
//import com.github.scribejava.core.model.Verb;
//import com.github.scribejava.core.oauth.OAuth20Service;
//import com.pavllo.oauthtester.components.IdamApi20;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Random;
//import java.util.Scanner;
//import java.util.concurrent.ExecutionException;
//
//@Service
//public class OAuthClient {
//
//    private static final String PROTECTED_RESOURCE_URL = "https://localhost:8765/oauth/me?tenant=tenant1";
//    public static final HashMap<String, OAuth20Service> secrets = new HashMap<>();
//
//    @PostConstruct
//    public void a() throws InterruptedException, ExecutionException, IOException {
//        OAuth20Service service = new ServiceBuilder("843cc8c1-8b02-4c26-a985-ef3cee134fe8")
//                .apiSecret("QcOSQIAxLGG7lLjZziIzWU")
//                .defaultScope("read")
//                .callback("http://example.com/callback")
//                .build(IdamApi20.instance());
//        final Scanner in = new Scanner(System.in);
//
//        System.out.println();
//
//        // Obtain the Authorization URL
//        System.out.println("Fetching the Authorization URL...");
//        final String secretState = "secret" + new Random().nextInt(999_999);
//        secrets.put(secretState,service);
//        final String authorizationUrl = service.createAuthorizationUrlBuilder()
//                .state(secretState)
//                .build();
//        System.out.println("Got the Authorization URL!");
//        System.out.println("Now go and authorize ScribeJava here:");
//        System.out.println(authorizationUrl);
//        System.out.print(">>");
//        String code = in.nextLine();
//        System.out.println();
//        System.out.println("Trading the Authorization Code for an Access Token...");
//        OAuth2AccessToken accessToken = null;
//        try {
//            accessToken = service.getAccessToken(code);
//
//        } catch (Exception ignored) {
//        }
//        while (accessToken == null) {
//            code = in.nextLine();
//            System.out.println();
//            System.out.println("Trading the Authorization Code for an Access Token...");
//            try {
//                accessToken = service.getAccessToken(code);
//
//            } catch (Exception e) {
//                accessToken = null;
//            }
//        }
//        System.out.println("Got the Access Token!");
//        System.out.println("(The raw response looks like this: " + accessToken.getRawResponse() + "')");
//
//        System.out.println("Refreshing the Access Token...");
//        accessToken = service.refreshAccessToken(accessToken.getRefreshToken());
//        System.out.println("Refreshed the Access Token!");
//        System.out.println("(The raw response looks like this: " + accessToken.getRawResponse() + "')");
//        System.out.println();
//
//        // Now let's go and ask for a protected resource!
//        System.out.println("Now we're going to access a protected resource...");
//        while (true) {
//            System.out.println("Paste fieldnames to fetch (leave empty to get profile, 'exit' to stop example)");
//            System.out.print(">>");
//            final String query = in.nextLine();
//            System.out.println();
//
//            final String requestUrl;
//            if ("exit".equals(query)) {
//                break;
//            } else if (query == null || query.isEmpty()) {
//                requestUrl = PROTECTED_RESOURCE_URL;
//            } else {
//                requestUrl = PROTECTED_RESOURCE_URL + "?fields=" + query;
//            }
//
//            final OAuthRequest request = new OAuthRequest(Verb.GET, requestUrl);
//            service.signRequest(accessToken, request);
//            System.out.println();
//            try (Response response = service.execute(request)) {
//                System.out.println(response.getCode());
//                System.out.println(response.getBody());
//            }
//            System.out.println();
//        }
//    }
//}
