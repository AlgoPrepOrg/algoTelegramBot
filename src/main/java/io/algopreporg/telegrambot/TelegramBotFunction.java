package io.algopreporg.telegrambot;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.algopreporg.telegrambot.telegram.TelegramFactory;
import io.algopreporg.telegrambot.telegram.model.TelegramRequest;

public class TelegramBotFunction {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public APIGatewayProxyRequestEvent handleRequest(APIGatewayProxyRequestEvent event, Context context)
            throws JsonProcessingException {
        var logger = context.getLogger();
        String request = event.getBody();
        logger.log("Read request " + request);

        TelegramRequest telegramRequest = objectMapper.readValue(request, TelegramRequest.class);
        logger.log("Update Id" + telegramRequest.getUpdate_id());
        logger.log("Message" + telegramRequest.getMessage());
        var response = TelegramFactory.handleRequest(telegramRequest);
        logger.log("Response " + response);
        return event;
    }
}
