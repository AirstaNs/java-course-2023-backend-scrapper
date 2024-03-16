package edu.java.bot.model.response;

import edu.java.bot.client.scrapper.dto.response.LinkResponse;
import java.util.List;

public record ListLinksResponse(
    List<LinkResponse> links,
    Integer size
) {
}
