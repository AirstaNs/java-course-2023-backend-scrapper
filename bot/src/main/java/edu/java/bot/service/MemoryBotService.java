package edu.java.bot.service;

import edu.java.bot.model.Link;
import edu.java.bot.model.response.LinkResponse;
import edu.java.bot.model.response.ListLinksResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;

@Service
public class MemoryBotService implements BotService {

    private final Map<Long, List<Link>> usersLinks = new ConcurrentHashMap<>();

    @Override
    public void registerUser(String name, Long id) {
        usersLinks.put(id, new CopyOnWriteArrayList<>());
    }

    @Override
    public LinkResponse addLinkToUser(String url, Long userId) {
        usersLinks.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>())
                  .add(new Link(url, UUID.randomUUID()));
        return new LinkResponse(true);
    }

    @Override
    public LinkResponse removeLinkFromUser(UUID linkId, Long userId) {
        usersLinks.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>())
                  .removeIf(link -> link.uuid().equals(linkId));
        return new LinkResponse(true);
    }

    @Override
    public ListLinksResponse listLinks(Long userId) {
        if (usersLinks.isEmpty()) {
            return new ListLinksResponse(List.of());
        }
        return new ListLinksResponse(Collections.unmodifiableList(usersLinks.get(userId)));
    }
}
