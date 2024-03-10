package edu.java.api.gtihub;

import edu.java.api.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GitHubClient extends ApiClient<GitHubResponse> {

    @Autowired
    public GitHubClient(WebClient githubWebClient) {
        super(githubWebClient);
    }

    public GitHubClient(String url) {
        super(url);
    }

    public GitHubResponse getUpdates(String owner, String repo) {
        String uri = "/repos/%s/%s".formatted(owner, repo);
        return fetch(uri, GitHubResponse.class, GitHubResponse.EMPTY);
    }
}
