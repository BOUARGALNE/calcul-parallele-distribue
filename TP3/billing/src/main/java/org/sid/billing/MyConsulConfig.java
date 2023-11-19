package org.sid.billing;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "token")
@Data
public class MyConsulConfig {

    //c'est les memes noms des propriétés déclaré dans Consul
    private long accessTokenTimeout;
    private long refreshTokenTimeout;
}
