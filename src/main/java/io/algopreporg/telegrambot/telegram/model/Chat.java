package io.algopreporg.telegrambot.telegram.model;

import java.util.Objects;

public class Chat {
    private Integer id;

    private String first_name;

    private String username;

    private String type;

    public Chat() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(id, chat.id) && Objects.equals(first_name, chat.first_name) && Objects.equals(username, chat.username) && Objects.equals(type, chat.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, username, type);
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", firstName='" + first_name + '\'' +
                ", username='" + username + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
