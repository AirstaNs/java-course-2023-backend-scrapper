package edu.java.bot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command {
    SendMessage handle(Update update);

    String command();

    String description();

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }

    default boolean supports(Update update) {
        return update.message() != null && update.message().text() != null
               && update.message().text().split(" +")[0].equals(command());
    }
}
