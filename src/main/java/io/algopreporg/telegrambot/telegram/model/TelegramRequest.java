package io.algopreporg.telegrambot.telegram.model;

import java.util.Objects;

public class TelegramRequest {

    private String update_id;

    private Message message;

    public TelegramRequest() {
    }

    public String getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(String update_id) {
        this.update_id = update_id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelegramRequest that = (TelegramRequest) o;
        return Objects.equals(update_id, that.update_id) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(update_id, message);
    }

    @Override
    public String toString() {
        return "TelegramRequest{" +
                "updateId='" + update_id + '\'' +
                ", message=" + message +
                '}';
    }
}
