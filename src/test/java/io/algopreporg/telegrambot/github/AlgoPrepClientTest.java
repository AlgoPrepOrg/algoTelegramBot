package io.algopreporg.telegrambot.github;

import io.algopreporg.telegrambot.github.model.Poll;
import io.algopreporg.telegrambot.http.CommunicationGeneralException;
import io.algopreporg.telegrambot.http.HttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AlgoPrepClientTest {

    private static final String URL = "https://algopreporg.github.io/poll/";
    public static final int CONNECTION_TIMEOUT = 500;
    public static final int READ_TIMEOUT = 500;

    private AlgoPrepClient testInstance;

    @BeforeEach
    void setUp() {
        var httpClient = new HttpClient(CONNECTION_TIMEOUT, READ_TIMEOUT);
        testInstance = new AlgoPrepClient(httpClient, URL);
    }

    @DisplayName("Should fetch csv file with poll data and create model")
    @Test
    void shouldFetchPollAndCreateIt() {
        //given
        var firstPoll = "1";
        Poll poll = new Poll(
                "Проголосуйте за останні 3 задачі",
                Arrays.asList("Вирішив", "Треба більше часу", "Нічого не зрозуміло. Чекаю коли зберемося.")
        );

        Optional<Poll> pollData = testInstance.getPollData(firstPoll);

        assertThat(pollData).isPresent()
                        .contains(poll);
    }


    @DisplayName("Should trow exception when csv file is missing")
    @Test
    void shouldThrowExceptionWhenCsvFileIsMissing() {
        //given
        var firstPoll = "0";

        Throwable thrown = catchThrowable(() -> testInstance.getPollData(firstPoll));

        assertThat(thrown).isInstanceOf(CommunicationGeneralException.class)
                .hasCauseInstanceOf(FileNotFoundException.class);
    }

}