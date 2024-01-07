package io.algopreporg.telegrambot.telegram;

import io.algopreporg.telegrambot.http.HttpClient;
import io.algopreporg.telegrambot.telegram.command.Command;
import io.algopreporg.telegrambot.telegram.command.impl.MdTelegramCommand;
import io.algopreporg.telegrambot.telegram.command.impl.PollTelegramCommand;
import io.algopreporg.telegrambot.telegram.command.impl.SelfTelegramCommand;
import io.algopreporg.telegrambot.telegram.model.Message;
import io.algopreporg.telegrambot.telegram.model.TelegramRequest;

import java.util.List;
import java.util.Objects;

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
        Objects.requireNonNull(request, "TelegramRequest must be not null");
        Message message = request.getMessage();

        if (Objects.nonNull(message) && Objects.nonNull(message.getChat()) && Objects.nonNull(message.getChat().getId())) {
            String text = message.getText();
            return COMMANDS.stream()
                    .filter(command -> command.isHandle(message.getText()))
                    .map(command -> command.execute(message.getText(), message.getChat().getId()))
                    .findAny()
                    .orElse(String.format(NO_HANDLER, text));
        }

        return String.format(NO_HANDLER, "if message is null");
    }
}
