package io.algopreporg.telegrambot.telegram.poll;

import io.algopreporg.telegrambot.telegram.poll.properties.AlgoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@RequiredArgsConstructor
@Configuration
public class PollConfiguration {

    private final AlgoProperties algoProperties;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(algoProperties.getConnectionTimeout()))
                .setReadTimeout(Duration.ofMillis(algoProperties.getReadTimeout()))
                .build();

    }
}
