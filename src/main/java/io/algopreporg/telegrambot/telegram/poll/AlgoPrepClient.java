package io.algopreporg.telegrambot.telegram.poll;

import io.algopreporg.telegrambot.telegram.poll.model.Poll;
import io.algopreporg.telegrambot.telegram.poll.properties.AlgoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AlgoPrepClient {
    private static final String FILE_TYPE = ".csv";
    private static final String SPLITERATOR = ";";

    private final AlgoProperties algoProperties;

    private final RestTemplate restTemplate;

    public Optional<Poll> getPollData(String pollFileName) {
        String url = algoProperties.getUrl() + pollFileName + FILE_TYPE;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String body = responseEntity.getBody();
            if (Objects.nonNull(body)) {
                String[] items = body.split(SPLITERATOR);

                if (items.length > 0) {
                    String title = items[0];
                    List<String> options = Arrays.asList(items).subList(1, items.length);
                    return Optional.of(new Poll(title, options));
                }
            }
        }

        return Optional.empty();
    }
}
