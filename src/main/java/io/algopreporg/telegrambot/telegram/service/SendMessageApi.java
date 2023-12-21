package io.algopreporg.telegrambot.telegram.service;

import io.algopreporg.telegrambot.telegram.Bot;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;

public interface SendMessageApi {

    void send(Bot bot, SendPoll sendPoll, Long sendTo);
}
