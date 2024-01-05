package io.algopreporg.telegrambot.telegram;

import io.algopreporg.telegrambot.github.model.Poll;
import io.algopreporg.telegrambot.http.HttpClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    private static final String FORM_URLENCODED = "application/x-www-form-urlencoded";
    private static final String TELEGRAM_SEND_MESSAGE = "https://api.telegram.org/bot%s/%s";
    private static final String TOKEN = "TELEGRAM_BOT_TOKEN";
    private static final String SEND_MESSAGE = "sendMessage";
    private static final String SEND_POLL = "sendPoll";
    private static final String DELIMITER = ",";
    public static final String TEXT = "text=";
    public static final String CHAT_ID_PARAM = "&chat_id=";
    public static final String DISABLE_WEB_PAGE_PREVIEW_TRUE_PARAM = "&disable_web_page_preview=true";
    public static final String PARSE_MODE_MARKDOWN_PARAM = "&parse_mode=markdown";
    private final String chatId;
    private final String botToken;
    private final HttpClient httpClient;

    public TelegramBotClient(String chatId, HttpClient httpClient) {
        this.chatId = chatId;
        this.httpClient = httpClient;
        this.botToken = System.getenv(TOKEN);
    }

    public TelegramBotClient(String chatId, String botToken, HttpClient httpClient) {
        this.chatId = chatId;
        this.botToken = botToken;
        this.httpClient = httpClient;
    }

    public String sendMessage(String message) {
        var telegramUrl = String.format(TELEGRAM_SEND_MESSAGE, botToken, SEND_MESSAGE);
        String createRequest = String.format(CHAT_ID_S_TEXT_S, chatId, message);
        return httpClient.sendPostMessage(telegramUrl, Map.of(CONTENT_TYPE, CONTENT_TYPE_VALUE), createRequest);
    }

    public String sendMarkDownMessage(String chatId, String message) {
        var telegramUrl = String.format(TELEGRAM_SEND_MESSAGE, botToken, SEND_MESSAGE);
        String formattedMessage = TEXT + URLEncoder.encode(message, StandardCharsets.UTF_8);
        String createRequest = formattedMessage +
                CHAT_ID_PARAM + chatId +
                DISABLE_WEB_PAGE_PREVIEW_TRUE_PARAM +
                PARSE_MODE_MARKDOWN_PARAM;

        return httpClient.sendPostMessage(telegramUrl, Map.of(CONTENT_TYPE, FORM_URLENCODED), createRequest);
    }

    public String poll(Poll poll) {
        var telegramUrl = String.format(TELEGRAM_SEND_MESSAGE, botToken, SEND_POLL);
        String optionsText = poll.options().stream()
                .map(option -> String.format(ITEM, option))
                .collect(Collectors.joining(DELIMITER));

        String createRequest = String.format(POLL_CHAT_ID_WITH_QUESTION_AND_OPTIONS, chatId, poll.title(), optionsText);

        return httpClient.sendPostMessage(telegramUrl, Map.of(CONTENT_TYPE, CONTENT_TYPE_VALUE), createRequest);
    }
}
