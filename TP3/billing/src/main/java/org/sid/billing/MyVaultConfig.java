package org.sid.billing;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Component
@ConfigurationProperties(prefix = "user") //dans le contexte @RefreshScope
@Data
public class MyVaultConfig {
    private String username;
    private String password;
    private String otp;
}
