package edu.java.bot.service;

import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemoryBotServiceTest {

    @Test
    public void registerUserShouldRegisterUser() {
        MemoryBotService service = new MemoryBotService();
        service.registerUser("user", 123L);
        Assertions.assertThat(service.listLinks(123L).links()).hasSize(0);
    }

    @Test
    public void addLinkToUserShouldLinkUrlToUser() {
        MemoryBotService service = new MemoryBotService();
        service.addLinkToUser("link", 123L);
        Assertions.assertThat(service.listLinks(123L).links())
                  .hasSize(1)
                  .element(0)
                  .extracting("url")
                  .isEqualTo("link");

    }

    @Test
    public void removeLinkFromUserShouldUnlinkUrlFromUser() {
        MemoryBotService service = new MemoryBotService();
        service.addLinkToUser("link", 123L);
        UUID linkId = service.listLinks(123L).links().getFirst().uuid();
        service.removeLinkFromUser(linkId, 123L);
        Assertions.assertThat(service.listLinks(123L).links()).hasSize(0);

    }

}
