package edu.java.scheduled;

import edu.java.api.gtihub.GitHubClient;
import edu.java.api.stackOverflow.StackOverflowClient;
import java.util.Arrays;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class LinkUpdaterScheduler {

    private final GitHubClient githubService;
    private final StackOverflowClient stackOverflowService;

    @Autowired
    public LinkUpdaterScheduler(GitHubClient githubService, StackOverflowClient stackOverflowService) {
        this.githubService = githubService;
        this.stackOverflowService = stackOverflowService;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        log.info("Executing update task");
        var owner = "AirstaNs";
        var repo = "java-course-2023-backend-scrapper";
        var gitHubResponse = githubService.getUpdates(owner, repo);

        long questionId = 71516916;
        var stackOverflowResponse = stackOverflowService.getQuestionInfo(questionId);
        log.info(gitHubResponse);
        log.info(Arrays.toString(stackOverflowResponse.items()));
        log.info("Done");
    }

}
