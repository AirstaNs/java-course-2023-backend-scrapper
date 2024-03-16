package edu.java.bot.service;

import java.net.URI;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemoryBotServiceTest {

    @Test
    public void registerUserShouldRegisterUser() {
        MemoryBotService service = new MemoryBotService();
        service.registerUser(123L);
        Assertions.assertThat(service.listLinks(123L).answer().links()).hasSize(0);
    }

    @Test
    public void addLinkToUserShouldLinkUrlToUser() {
        MemoryBotService service = new MemoryBotService();
        service.addLinkToUser("http://localhost2.ru", 123L);
        Assertions.assertThat(service.listLinks(123L).answer().links())
                  .hasSize(1)
                  .element(0)
                  .extracting("url")
                  .isEqualTo(URI.create("http://localhost2.ru"));

    }

    @Test
    public void removeLinkFromUserShouldUnlinkUrlFromUser() {
        MemoryBotService service = new MemoryBotService();
        service.addLinkToUser("id", 123L);
        Long linkId = service.listLinks(123L).answer().links().getFirst().id();
        service.removeLinkFromUser(linkId, 123L);
        Assertions.assertThat(service.listLinks(123L).answer().links()).hasSize(0);
    }

}
