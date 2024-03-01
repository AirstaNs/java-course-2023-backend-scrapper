package edu.java.bot.service;

import edu.java.bot.model.response.LinkResponse;
import edu.java.bot.model.response.ListLinksResponse;
import java.util.UUID;

public interface BotService {

    void registerUser(String name, Long id);

    LinkResponse addLinkToUser(String url, Long userId);

    LinkResponse removeLinkFromUser(UUID linkId, Long userId);

    ListLinksResponse listLinks(Long userId);
}
