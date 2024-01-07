package io.algopreporg.telegrambot.telegram.command.impl;

import io.algopreporg.telegrambot.http.HttpClient;
import io.algopreporg.telegrambot.telegram.TelegramBotClient;
import io.algopreporg.telegrambot.telegram.command.Command;

public class MdTelegramCommand implements Command {

    private static final String MD = "/md";
    private static final String EMPTY = "";
    private static final String SPACE = " ";

    private final HttpClient httpClient;

    public MdTelegramCommand(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public boolean isHandle(String messageText) {
        return messageText.startsWith(MD);
    }

    @Override
    public String execute(String text, String chatId) {
        String messageText = text.replace(MD + SPACE, EMPTY);

        var telegramBotClient = new TelegramBotClient(chatId, httpClient);
        return telegramBotClient.sendMarkDownMessage(chatId, messageText);
    }
}
