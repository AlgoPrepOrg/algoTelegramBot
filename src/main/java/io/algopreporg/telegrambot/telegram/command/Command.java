package io.algopreporg.telegrambot.telegram.command;

import io.algopreporg.telegrambot.telegram.model.Message;

public interface Command {

    boolean isHandle(Message message);

    String execute(Message message);
}
