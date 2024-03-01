package edu.java.bot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import edu.java.bot.resolver.TextResolver;

public abstract class AbstractCommand implements Command {
    protected TextResolver textResolver;

    public AbstractCommand(TextResolver textResolver) {
        this.textResolver = textResolver;
    }

    @Override
    public BotCommand toApiCommand() {
        return new BotCommand(command(), textResolver.resolve(description()));
    }
}
