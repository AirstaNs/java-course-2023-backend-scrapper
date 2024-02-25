package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.api.stackOverflow.StackOverflowClient;
import edu.java.api.stackOverflow.StackOverflowItem;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StackOverflowClientTest {
    private static WireMockServer wireMockServer;
    private static StackOverflowClient client;

    @BeforeAll
    public static void setUp() {
        String url = "/questions/71516916?site=stackoverflow";
        wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
        wireMockServer.stubFor(get(urlEqualTo(url))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("""
                    {
                        "items": [
                            {
                                "title": "Spring, injecting a list of beans with only specific beans included",
                                "last_activity_date": 1647540794
                            }
                        ]
                    }
                    """)));
        wireMockServer.start();
        String baseUrl = wireMockServer.baseUrl();
        client = new StackOverflowClient(baseUrl);
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void getQuestionInfo_ShouldReturnCorrectItem_WhenGivenQuestionId() {
        long questionId = 71516916;
        var stackOverflowItem = client.getQuestionInfo(questionId).items()[0];

        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(Instant.ofEpochSecond(1647540794), ZoneOffset.UTC);
        StackOverflowItem item = new StackOverflowItem("Spring, injecting a list of beans with only specific beans included",
            offsetDateTime
        );
        assertEquals(stackOverflowItem, item);
    }

    @Test
    public void getQuestionInfo_ShouldHandleUnknownQuestionId() {
        long unknownQuestionId = 5555555;
        var stackOverflowResponse = client.getQuestionInfo(unknownQuestionId);

        assertNotNull(stackOverflowResponse);
        assertNull(stackOverflowResponse.items());
    }
}
