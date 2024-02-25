package edu.java.api.stackOverflow;

public record StackOverflowResponse(StackOverflowItem[] items) {
    public static final StackOverflowResponse EMPTY = new StackOverflowResponse(null);
}
