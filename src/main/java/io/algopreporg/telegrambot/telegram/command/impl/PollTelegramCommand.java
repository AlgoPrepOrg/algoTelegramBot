package io.algopreporg.telegrambot.telegram.command.impl;

import io.algopreporg.telegrambot.github.AlgoPrepClient;
import io.algopreporg.telegrambot.http.HttpClient;
import io.algopreporg.telegrambot.telegram.TelegramBotClient;
import io.algopreporg.telegrambot.telegram.command.Command;

public class PollTelegramCommand implements Command {
    private static final String POLL = "/poll";
    private static final String EMPTY = "";
    private static final String SPACE = " ";

    private final HttpClient httpClient;
    private final AlgoPrepClient algoPrepClient;

    public PollTelegramCommand(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.algoPrepClient = new AlgoPrepClient(httpClient);
    }

    @Override
    public boolean isHandle(String messageText) {
        return messageText.startsWith(POLL);
    }

    @Override
    public String execute(String text, String chatId) {
        String pollId = text.replace(POLL + SPACE, EMPTY);

        var telegramBotClient = new TelegramBotClient(chatId, httpClient);

        return algoPrepClient.getPollData(pollId)
                .map(telegramBotClient::poll)
                .orElse(null);
    }
}
