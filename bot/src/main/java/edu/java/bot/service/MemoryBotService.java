package edu.java.bot.service;

import edu.java.bot.client.scrapper.dto.response.LinkResponse;
import edu.java.bot.client.scrapper.dto.response.ListLinksResponse;
import edu.java.bot.dto.OptionalAnswer;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

//@Service
public class MemoryBotService implements BotService {

    private final Map<Long, List<LinkResponse>> usersLinks = new ConcurrentHashMap<>();

    @Override
    public void registerUser(Long id) {
        usersLinks.put(id, new CopyOnWriteArrayList<>());
    }

    @Override
    public OptionalAnswer<LinkResponse> addLinkToUser(String url, Long userId) {
        usersLinks.computeIfAbsent(userId, k ->
                      new CopyOnWriteArrayList<>()).add(new LinkResponse(1L, URI.create(url)));
        return OptionalAnswer.of(new LinkResponse(1L, URI.create(url)));
    }

    @Override
    public OptionalAnswer<LinkResponse> removeLinkFromUser(Long linkId, Long userId) {
        usersLinks.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>())
                  .removeIf(link -> link.id().equals(linkId));
        return OptionalAnswer.of(new LinkResponse(1L, URI.create("https://github.com/AirstaNs")));
    }

    @Override
    public OptionalAnswer<ListLinksResponse> listLinks(Long userId) {
        if (usersLinks.isEmpty()) {
            return OptionalAnswer.of(new ListLinksResponse(List.of(), 0));
        }
        return OptionalAnswer.of(new ListLinksResponse(
            Collections.unmodifiableList(usersLinks.get(userId)),
            usersLinks.get(userId).size()
        ));
    }
}
