package edu.java.bot.service;

import edu.java.bot.client.scrapper.dto.response.LinkResponse;
import edu.java.bot.client.scrapper.dto.response.ListLinksResponse;
import edu.java.bot.dto.OptionalAnswer;

public interface BotService {

    void registerUser(Long id);

    OptionalAnswer<LinkResponse> addLinkToUser(String url, Long userId);

    OptionalAnswer<LinkResponse> removeLinkFromUser(Long linkId, Long userId);

    OptionalAnswer<ListLinksResponse> listLinks(Long userId);

}
