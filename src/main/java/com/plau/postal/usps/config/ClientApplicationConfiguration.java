package com.plau.postal.usps.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("postal.usps")
public class ClientApplicationConfiguration {
    
}
