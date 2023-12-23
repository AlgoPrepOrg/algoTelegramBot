package io.algopreporg.telegrambot.telegram;

import io.algopreporg.telegrambot.github.model.Poll;
import io.algopreporg.telegrambot.http.HttpClient;

import java.util.Map;
import java.util.stream.Collectors;

public class TelegramBotClient {
    private static final String CHAT_ID_S_TEXT_S = """
                {
                    "chat_id":"%s",
                    "text":"%s"
                 }
            """;

    private static final String POLL_CHAT_ID_WITH_QUESTION_AND_OPTIONS = """
                {
                    "chat_id":"%s",
                    "question":"%s",
                    "options":[%s]
                    }
            """;

    private static final String ITEM = """
                "%s"
            """;
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "application/json";

    private static final String TELEGRAM_SEND_MESSAGE = "https://api.telegram.org/bot%s/%s";
    private static final String SEND_MESSAGE = "sendMessage";
    private static final String SEND_POLL = "sendPoll";
    private static final String DELIMITER = ",";
    private final Integer chatId;
    private final String botToken;
    private final HttpClient httpClient;
    private String telegramUrl;

    public TelegramBotClient(Integer chatId, String botToken, HttpClient httpClient) {
        this.chatId = chatId;
        this.botToken = botToken;
        this.httpClient = httpClient;
    }

    public String sendMessage(String message) {
        this.telegramUrl = String.format(TELEGRAM_SEND_MESSAGE, botToken, SEND_MESSAGE);
        String createRequest = String.format(CHAT_ID_S_TEXT_S, chatId, message);
        return httpClient.sendPostMessage(telegramUrl, Map.of(CONTENT_TYPE, CONTENT_TYPE_VALUE), createRequest);
    }

    public String poll(Poll poll) {
        this.telegramUrl = String.format(TELEGRAM_SEND_MESSAGE, botToken, SEND_POLL);
        try {
            String optionsText = poll.options().stream()
                    .map(option -> String.format(ITEM, option))
                    .collect(Collectors.joining(DELIMITER));

            String createRequest = String.format(POLL_CHAT_ID_WITH_QUESTION_AND_OPTIONS, chatId, poll.title(), optionsText);

            return httpClient.sendPostMessage(telegramUrl, Map.of(CONTENT_TYPE, CONTENT_TYPE_VALUE), createRequest);
        } catch (Exception exe) {
            throw new TelegramGeneralException("Cannot send poll to chatId " + chatId, exe);
        }
    }
}
