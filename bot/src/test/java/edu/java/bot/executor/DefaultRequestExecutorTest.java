package edu.java.bot.executor;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DefaultRequestExecutorTest {

    @Test
    public void executeShouldThrowIllegalStateExceptionWhenTelegramBotNotSet() {
        RequestExecutor executor = new DefaultRequestExecutor(null);
        Assertions.assertThatThrownBy(() -> executor.execute(new SendMessage(1, "Test message")))
                  .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void executeShouldExecuteWhenTelegramBotSet() {
        TelegramBot mockTelegramBot = Mockito.mock(TelegramBot.class);
        RequestExecutor executor = new DefaultRequestExecutor(mockTelegramBot);

        executor.execute(new SendMessage(1, "Test message"));

        Mockito.verify(mockTelegramBot, Mockito.times(1)).execute(Mockito.any(SendMessage.class));
    }
}
