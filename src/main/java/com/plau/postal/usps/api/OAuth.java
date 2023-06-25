package com.plau.postal.usps.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plau.postal.usps.client.HTTPClient;

import lombok.Data;

@Service
public class OAuth {
    
    @Autowired
    private HTTPClient client;

    private String endpoint = "https://api.usps.com/oauth2/v3";

    public OAuth2Token token(OAuth2Request request){
        OAuth2Token response = new OAuth2Token();

        String api = endpoint + "/token";

        URI uri = UriComponentsBuilder.fromHttpUrl(api)
        .build()
        .toUri();

        client.postForObject(uri, request, OAuth2Token.class);
        return response; 
    }

    public enum GrantType {
        CLIENT_CREDENTIALS("client_credentials"),
        AUTHORIZATION_CODE("authorization_code"),
        REFRESH_TOKEN("refresh_token");

        private final String code;

        GrantType(final String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    @Data
    public static class OAuth2Request {

        @JsonProperty("token_type")
        private String grantType;

        @JsonProperty("scope")
        private String scope;

        @JsonProperty("state")
        private String state;

        @JsonProperty("client_id")
        private String clientId;

        @JsonProperty("client_secret")
        private String clientSecret;

        @JsonProperty("code")
        private String code;

        @JsonProperty("redirect_uri")
        private String redirectURI;

        @JsonProperty("refresh_token")
        private String refreshToken;
    }

    @Data
    public static class OAuth2Token {
        @JsonProperty("application_name")
        private String applicationName;

        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("token_type")
        private String tokenType;

        @JsonProperty("status")
        private String status;

        @JsonProperty("issuer")
        private String issuer;
        
        @JsonProperty("issued_at")
        private String issuedAt;

        @JsonProperty("expires_in")
        private Integer expiresIn;

        @JsonProperty("scope")
        private String scope;

        @JsonProperty("state")
        private String state;

        @JsonProperty("public_key")
        private String publicKey;

        @JsonProperty("refresh_token")
        private String refreshToken;

        @JsonProperty("refresh_token_issued_at")
        private String refreshTokenIssuedAt;

        @JsonProperty("refresh_token_expires_in")
        private String refreshTokenExpiresIn;
        
        @JsonProperty("refresh_count")
        private Integer refreshCount;

        @JsonProperty("client_id")
        private String clientId;

        @JsonProperty("api_products")
        private String apiProducts;
    }
}
