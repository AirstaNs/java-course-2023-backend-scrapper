package edu.java.bot.model.response;

public record LinkResponse(boolean success, String errorMessage) {
    public LinkResponse(boolean success) {
        this(success, "");
    }
}
