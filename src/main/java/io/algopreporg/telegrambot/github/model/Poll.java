package io.algopreporg.telegrambot.github.model;

import java.util.List;

public record Poll(String title, List<String> options) {
}
