package swt6.spring.worklog.client.viewmodels;

public interface IssueUiProcessFacade {
    String createCommand();

    String listIssues(String option1, String option2, String option3);

    String updateCommand(String option);
}
