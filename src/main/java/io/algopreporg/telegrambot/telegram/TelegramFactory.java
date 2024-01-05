package io.algopreporg.telegrambot.telegram;

import io.algopreporg.telegrambot.http.HttpClient;
import io.algopreporg.telegrambot.telegram.command.Command;
import io.algopreporg.telegrambot.telegram.command.impl.MdTelegramCommand;
import io.algopreporg.telegrambot.telegram.command.impl.PollTelegramCommand;
import io.algopreporg.telegrambot.telegram.command.impl.SelfTelegramCommand;
import io.algopreporg.telegrambot.telegram.model.Message;
import io.algopreporg.telegrambot.telegram.model.TelegramRequest;

import java.util.List;

public class TelegramFactory {
    private static final String NO_HANDLER = "No handler for this request: %s";

    private static final List<Command> COMMANDS;

    static {
        var httpClient = new HttpClient();

        COMMANDS = List.of(
                new SelfTelegramCommand(httpClient),
                new PollTelegramCommand(httpClient),
                new MdTelegramCommand(httpClient)
        );
    }

    private TelegramFactory(){

    }

    public static String handleRequest(TelegramRequest request) {
        Message message = request.getMessage();
        String text = message.getText();
        return COMMANDS.stream()
                .filter(command -> command.isHandle(message))
                .map(command -> command.execute(message))
                .findAny()
                .orElse(String.format(NO_HANDLER, text));
    }
}
