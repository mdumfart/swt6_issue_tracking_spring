package swt6.spring.worklog.client.viewmodels;

public interface ProjectUiProcessFacade {
    String createCommand();

    String updateCommand(String option);

    void printAllProjects();

    String listProjects(String option);
}
