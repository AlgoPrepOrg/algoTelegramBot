package io.algopreporg.telegrambot.telegram.model;

import java.util.Objects;

public class From {
    private String id;

    private boolean is_bot;

    private String first_name;

    private String username;

    private String language_code;

    public From() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIs_bot() {
        return is_bot;
    }

    public void setIs_bot(boolean is_bot) {
        this.is_bot = is_bot;
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

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        From from = (From) o;
        return is_bot == from.is_bot && Objects.equals(id, from.id) && Objects.equals(first_name, from.first_name) && Objects.equals(username, from.username) && Objects.equals(language_code, from.language_code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, is_bot, first_name, username, language_code);
    }

    @Override
    public String toString() {
        return "From{" +
                "id=" + id +
                ", bot=" + is_bot +
                ", firstName='" + first_name + '\'' +
                ", username='" + username + '\'' +
                ", languageCode='" + language_code + '\'' +
                '}';
    }
}
