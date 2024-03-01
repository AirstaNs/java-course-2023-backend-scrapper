package edu.java.bot.listener;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.executor.RequestExecutor;
import edu.java.bot.manager.MessagesManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BotMessagesListener implements UpdatesListener {

    private final RequestExecutor requestExecutor;
    private final MessagesManager messagesManager;

    @Autowired
    public BotMessagesListener(MessagesManager messagesManager, RequestExecutor requestExecutor) {
        this.messagesManager = messagesManager;
        this.requestExecutor = requestExecutor;
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            SendMessage sendMessage = messagesManager.process(update);
            if (sendMessage != null) {
                requestExecutor.execute(sendMessage.parseMode(ParseMode.Markdown));
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
