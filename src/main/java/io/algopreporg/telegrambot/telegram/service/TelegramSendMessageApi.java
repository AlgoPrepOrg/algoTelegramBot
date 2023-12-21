package io.algopreporg.telegrambot.telegram.service;

import io.algopreporg.telegrambot.telegram.Bot;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Slf4j
@Service
public class TelegramSendMessageApi implements SendMessageApi {

    @Override
    public void send(Bot bot, SendPoll sendPoll, Long sendTo) {
        try{
            log.info("Send message to chatId {} preparing...", sendTo);
            bot.execute(sendPoll);
            log.info("Send message to chatId {} completed with wait 1 sec", sendTo);
            //Added sleep to not reach limit
            TimeUnit.SECONDS.sleep(1);
        } catch (TelegramApiRequestException exe) {
            log.info("Can't send message {}, looks like user stop using bot, chatId {}",
                    exe.getMessage(), sendTo);
        } catch (Exception exe) {
            log.error("Can't send message to {}", sendTo, exe);
        }
    }
}
