package io.algopreporg.telegrambot.telegram.command;


public interface Command {

    boolean isHandle(String messageText);

    String execute(String messageText, String chatId);
}
