package io.algopreporg.telegrambot.telegram.poll.model;

import java.util.List;

public record Poll(String title, List<String> options) {
}
