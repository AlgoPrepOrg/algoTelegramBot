package io.algopreporg.telegrambot.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static io.algopreporg.telegrambot.http.RequestMethod.GET;
import static io.algopreporg.telegrambot.http.RequestMethod.POST;

public class HttpClient {
    private static final String IDENTITY = "";

    private final int connectionTimeout;
    private final int readTimeout;

    public HttpClient(int connectionTimeout, int readTimeout) {
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
    }

    public String sendGetMessage(String url, Map<String, String> requestProperties) {
        HttpURLConnection connection = null;
        try {
            connection = openConnection(url, GET);
            for (var entry : requestProperties.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }

            return sendToGetWithFile(connection);
        } catch (Exception exe) {
            throw new CommunicationGeneralException("Cannot send message to url " + url + " got error: " + exe.getMessage(), exe);
        } finally {
            disconnect(connection);
        }
    }


    public String sendPostMessage(String url, Map<String, String> requestProperties, String requestMessage) {
        HttpURLConnection connection = null;
        try {
            connection = openConnection(url, POST);
            connection.setDoOutput(true);
            for (var entry : requestProperties.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            return send(connection, requestMessage);
        } catch (Exception exe) {
            throw new CommunicationGeneralException("Cannot send message to url " + url + " got error: " + exe.getMessage(), exe);
        } finally {
            disconnect(connection);
        }
    }

    private static void disconnect(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
        }
    }

    public HttpURLConnection openConnection(String url, RequestMethod requestMethod) throws IOException {
        var sendUrl = new URL(url);

        var connection = (HttpURLConnection) sendUrl.openConnection();
        connection.setRequestMethod(requestMethod.name());
        connection.setConnectTimeout(connectionTimeout);
        connection.setReadTimeout(readTimeout);

        return connection;
    }

    private static String send(HttpURLConnection connection, String payload) throws IOException {
        try (var outputStream = connection.getOutputStream()) {
            outputStream.write(payload.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }

        try (var reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return reader.lines().reduce(IDENTITY, (partialResponse, line) -> partialResponse + line);
        }
    }

    private static String sendToGetWithFile(HttpURLConnection connection) throws IOException {
        try (var inputStream = connection.getInputStream()) {
            var result = new ByteArrayOutputStream();
            inputStream.transferTo(result);
            return result.toString(StandardCharsets.UTF_8);
        }
    }
}
