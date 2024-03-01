package edu.java.bot.listener;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.executor.RequestExecutor;
import edu.java.bot.manager.MessagesManager;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BotMessagesListenerTest {

    @Test
    public void processShouldAcceptUpdates() {
        BotMessagesListener listener = new BotMessagesListener(createMockUserMessagesProcessor(), createMockExecutor());
        Assertions.assertThat(listener.process(List.of(new Update()))).isEqualTo(UpdatesListener.CONFIRMED_UPDATES_ALL);
    }

    private RequestExecutor createMockExecutor() {
        return Mockito.mock(RequestExecutor.class);
    }

    private MessagesManager createMockUserMessagesProcessor() {
        return Mockito.mock(MessagesManager.class);
    }

}
