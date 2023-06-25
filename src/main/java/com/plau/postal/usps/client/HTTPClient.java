package com.plau.postal.usps.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plau.postal.usps.api.OAuth.OAuth2Token;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
public class HTTPClient extends RestTemplate {
    private final String token_uri = "https://api.usps.com/oauth2/v3/token";

    @Value("${postal.usps.client-id}")
    private String clientId;

    @Value("${postal.usps.client-secret}")
    private String clientSecret;

    private String token;

    private void registerOAuth() {
        ClientCredentialsRequest request = new ClientCredentialsRequest();
        request.setClientId(clientId);
        request.setClientSecret(clientSecret);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ClientCredentialsRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<OAuth2Token> response = super.exchange(token_uri, HttpMethod.POST, entity, OAuth2Token.class);
        OAuth2Token body = response.getBody();
        token = body.getAccessToken();
    }

    public <Response, Request> Response authenticatedHttpRequest(String url, HttpMethod method, Request request,
            Class<Response> responseType) {
        if (token == null) {
            registerOAuth();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Request> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Response> response = super.exchange(url, method, entity, responseType);

        return response.getBody();
    }

    @Data
    private static class ClientCredentialsRequest {

        @JsonProperty("grant_type")
        private String grantType = "client_credentials";

        @JsonProperty("client_id")
        private String clientId;

        @JsonProperty("client_secret")
        private String clientSecret;

    }
}
