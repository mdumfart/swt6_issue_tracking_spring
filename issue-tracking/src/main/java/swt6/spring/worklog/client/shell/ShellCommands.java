package swt6.spring.worklog.client.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import swt6.spring.worklog.client.viewmodels.*;
import swt6.spring.worklog.client.util.AppEntity;

@ShellComponent
public class ShellCommands {
    private final UtilDriver utilDriver;
    private final IssueUiProcessFacade issueUiProcessFacade;
    private final ProjectUiProcessFacade projectUiProcessFacade;
    private final EmployeeUiProcessFacade employeeUiProcessFacade;

    public ShellCommands(UtilDriver validateDriver,
                         IssueUiProcessFacade issueUiProcessFacade,
                         ProjectUiProcessFacade projectUiProcessFacade,
                         EmployeeUiProcessFacade employeeUiProcessFacade) {
        this.utilDriver = validateDriver;
        this.issueUiProcessFacade = issueUiProcessFacade;
        this.projectUiProcessFacade = projectUiProcessFacade;
        this.employeeUiProcessFacade = employeeUiProcessFacade;
    }

    @ShellMethod("Create an instance of an object")
    public String create(@ShellOption String kind) {
        AppEntity e = utilDriver.validateCreateParam(kind);

        if (e == null)
            return String.format("'%s' is not a supported entity", kind);

        if (e == AppEntity.project) {
            return projectUiProcessFacade.createCommand();
        }
        else {
            return issueUiProcessFacade.createCommand();
        }
    }

    @ShellMethod("List employees, projects or issues")
    public String list(
            @ShellOption String kind,
            @ShellOption(defaultValue = "") String option1,
            @ShellOption(defaultValue = "") String option2,
            @ShellOption(defaultValue = "") String option3) {
        AppEntity e = utilDriver.validateListParam(kind);

        if (e == null)
            return String.format("'%s' is not a supported entity", kind);

        StringBuffer sb = new StringBuffer();

        if (e == AppEntity.employee) {
            return employeeUiProcessFacade.listEmployeeCommand(option1);
        }

        if (e == AppEntity.project) {
            return projectUiProcessFacade.listProjects(option1);
        }

        if (e == AppEntity.issue) {
            return issueUiProcessFacade.listIssues(option1, option2, option3);
        }

        return "Something went wrong";
    }

    @ShellMethod("Update projects or issues")
    public String update(@ShellOption String kind, @ShellOption(defaultValue = "") String option) {
        AppEntity e = utilDriver.validateUpdateParam(kind);

        if (e == null)
            return String.format("'%s' is not a supported entity", kind);

        if (e == AppEntity.project) {
            return projectUiProcessFacade.updateCommand(option);
        }

        if (e == AppEntity.issue) {
            return issueUiProcessFacade.updateCommand(option);
        }

        return "Something went wrong";
    }
}
