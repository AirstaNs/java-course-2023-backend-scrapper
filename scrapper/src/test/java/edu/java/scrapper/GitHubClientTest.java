package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.api.gtihub.GitHubClient;
import edu.java.api.gtihub.GitHubResponse;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GitHubClientTest {
    private static WireMockServer wireMockServer;
    private static GitHubClient client;
    @BeforeAll
    public static void setUp() {
        String url = "/repos/AirstaNs/java-course-2023-backend-scrapper";
        wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
        wireMockServer.stubFor(get(urlEqualTo(url))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("""
                    {
                        "full_name": "AirstaNs/java-course-2023-backend-scrapper",
                        "updated_at": "2024-02-18T19:08:19Z",
                        "description": null
                    }
                    """)));
        wireMockServer.start();
        String WireUrl = wireMockServer.baseUrl();
        client = new GitHubClient(WireUrl);
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void getUpdatesShouldReturnCorrectUpdate() {
        var owner = "AirstaNs";
        var repo = "java-course-2023-backend-scrapper";
        var gitHubResponse = client.getUpdates(owner, repo);
        GitHubResponse gitHubResponseExpected = new GitHubResponse("AirstaNs/java-course-2023-backend-scrapper",
            OffsetDateTime.parse("2024-02-18T19:08:19Z"),
            null
        );
        assertEquals(gitHubResponseExpected, gitHubResponse);
    }
    @Test
    public void getUpdatesShouldReturnNullFields() {
        var owner = "AirstaNs";
        var repo = "not-found";
        var gitHubResponse = client.getUpdates(owner, repo);
        var expected = new GitHubResponse(null, null, null);
        assertEquals(expected, gitHubResponse);
    }
}
