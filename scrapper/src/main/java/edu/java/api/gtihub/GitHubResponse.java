package edu.java.api.gtihub;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubResponse(
    @JsonProperty("full_name")
    String fullName,
    @JsonProperty("updated_at")
    OffsetDateTime lastModified,
    String description) {
    public static final GitHubResponse EMPTY = new GitHubResponse(null, null, null);
}
