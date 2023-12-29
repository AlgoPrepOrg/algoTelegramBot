package io.algopreporg.telegrambot.telegram.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    private String message_id;

    private From from;

    private Chat chat;

    private Date date;

    private String text;

    public Message() {
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(message_id, message.message_id) && Objects.equals(from, message.from) && Objects.equals(chat, message.chat) && Objects.equals(date, message.date) && Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message_id, from, chat, date, text);
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId='" + message_id + '\'' +
                ", from=" + from +
                ", chat=" + chat +
                ", date=" + date +
                ", text='" + text + '\'' +
                '}';
    }
}
