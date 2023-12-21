package io.algopreporg.telegrambot.telegram;

import io.algopreporg.telegrambot.telegram.properties.BotProperties;
import io.algopreporg.telegrambot.telegram.service.SendMessageApi;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final BotProperties botProperties;
    private final TelegramManager telegramManager;
    private final SendMessageApi sendMessageApi;

    @PostConstruct
    public void onInit() {
        log.info("TelegramBotsApi username {} configured...", getBotUsername());
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Received message {}", update);
        var action = telegramManager.handle(update);
        var message = update.getMessage();
        Long sendTo = Objects.nonNull(message) ? message.getChatId() : update.getCallbackQuery().getMessage().getChatId();

        if (action instanceof SendPoll sendPoll){
            sendPoll.setChatId(sendTo);
            sendMessageApi.send(this, sendPoll, sendTo);
        }
    }

    @Override
    public String getBotUsername() {
        return botProperties.getUsername();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }
}
