package io.algopreporg.telegrambot.github;

import io.algopreporg.telegrambot.github.model.Poll;
import io.algopreporg.telegrambot.http.HttpClient;

import java.util.*;

public class AlgoPrepClient {

    private static final String FILE_TYPE = ".csv";
    private static final String SPLITERATOR = ";";

    private final HttpClient httpClient;
    private final String algoUrl;

    public AlgoPrepClient(HttpClient httpClient, String algoUrl) {
        this.httpClient = httpClient;
        this.algoUrl = algoUrl;
    }

    public Optional<Poll> getPollData(String pollFileName) {
        String url = algoUrl + pollFileName + FILE_TYPE;

        String body = httpClient.sendGetMessage(url, new HashMap<>());

        if (Objects.nonNull(body)) {
            String[] items = body.split(SPLITERATOR);

            if (items.length > 0) {
                String title = items[0];
                List<String> options = Arrays.stream(items)
                        .skip(1)
                        .map(String::trim)
                        .toList();
                return Optional.of(new Poll(title, options));
            }
        }

        return Optional.empty();
    }
}
