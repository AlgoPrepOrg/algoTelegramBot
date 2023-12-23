package io.algopreporg.telegrambot.telegram;

import io.algopreporg.telegrambot.github.AlgoPrepClient;
import io.algopreporg.telegrambot.github.model.Poll;
import io.algopreporg.telegrambot.http.HttpClient;
import io.algopreporg.telegrambot.telegram.model.Message;
import io.algopreporg.telegrambot.telegram.model.TelegramRequest;

import java.util.Optional;

public class TelegramFactory {
    private static final String GITHUB_ALGO_URL = "GITHUB_ALGO_URL";
    private static final String TOKEN = "TELEGRAM_BOT_TOKEN";
    private static final String CONNECTION_TIMEOUT_NAME = "TELEGRAM_BOT_CONNECTION_TIMEOUT";
    private static final String READ_TIMEOUT_NAME = "TELEGRAM_BOT_READ_TIMEOUT";
    private static final String SELF = "/self";
    private static final String EMPTY = "";
    private static final String POLL = "/poll";
    private static final String SPACE = " ";

    private static final String NO_HANDLER = "No handler for this request: %s";

    private TelegramFactory(){}

    public static String handleRequest(TelegramRequest request) {
        Message message = request.getMessage();
        String text = message.getText();
        Integer chatId = message.getChat().getId();

        int connectionTimeOut = Integer.parseInt(System.getenv(CONNECTION_TIMEOUT_NAME));
        int readTimeout = Integer.parseInt(System.getenv(READ_TIMEOUT_NAME));
        var httpClient = new HttpClient(connectionTimeOut, readTimeout);

        String botToken = System.getenv(TOKEN);
        var telegramBotClient = new TelegramBotClient(chatId, botToken, httpClient);

        if (text.startsWith(SELF)) {
            String messageText = text.replace(SELF + SPACE, EMPTY);
            return telegramBotClient.sendMessage(messageText);
        } else if (text.startsWith(POLL)) {
            String githubAlgoUrl = System.getenv(GITHUB_ALGO_URL);
            String pollId = text.replace(POLL + SPACE, EMPTY);

            var algoPrepClient = new AlgoPrepClient(httpClient, githubAlgoUrl);
            Optional<Poll> pollData = algoPrepClient.getPollData(pollId);
            if (pollData.isPresent()) {
                return telegramBotClient.poll(pollData.get());
            }
        }

        return String.format(NO_HANDLER, text);
    }
}
