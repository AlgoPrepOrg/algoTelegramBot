package io.algopreporg.telegrambot;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.algopreporg.telegrambot.telegram.TelegramFactory;
import io.algopreporg.telegrambot.telegram.model.TelegramRequest;

public class TelegramBotFunction implements RequestHandler<TelegramRequest, Void> {

    public Void handleRequest(TelegramRequest request, Context context) {
        var logger = context.getLogger();
        logger.log("Read request " + request);
        var response = TelegramFactory.handleRequest(request);
        logger.log("Response " + response);
        return null;
    }
}
