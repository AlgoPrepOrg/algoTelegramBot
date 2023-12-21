package io.algopreporg.telegrambot.telegram.poll.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "github.algo")
public class AlgoProperties {
    private long connectionTimeout;
    private long readTimeout;
    private String url;
}
