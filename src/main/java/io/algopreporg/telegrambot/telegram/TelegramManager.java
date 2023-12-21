package io.algopreporg.telegrambot.telegram;

import io.algopreporg.telegrambot.telegram.poll.AlgoPrepClient;
import io.algopreporg.telegrambot.telegram.poll.model.Poll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramManager {

    public static final String CREATE_POLL = "/createPoll";
    public static final String EMPTY = "";
    public static final String SPACE = " ";
    private final AlgoPrepClient algoPrepClient;

    public Object handle(Update update) {
        String command;

        if (update.hasCallbackQuery()) {
            command = update.getCallbackQuery().getData();
        } else {
            command = update.getMessage().getText();
        }

        if (command.startsWith(CREATE_POLL)) {
            String pollId = command.replace(CREATE_POLL + SPACE, EMPTY);

            return algoPrepClient.getPollData(pollId)
                    .map(this::createPoll)
                    .orElse(null);
        }

        return null;
    }

    private SendPoll createPoll(Poll poll) {
        var sendPollRequest = new SendPoll();
        sendPollRequest.setQuestion(poll.title());
        sendPollRequest.setOptions(poll.options());
        return sendPollRequest;
    }

}
