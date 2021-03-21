package swt6.spring.worklog.client.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import swt6.spring.worklog.client.beans.EmployeeDriver;
import swt6.spring.worklog.client.beans.IssueDriver;
import swt6.spring.worklog.client.beans.ProjectDriver;
import swt6.spring.worklog.client.beans.UtilDriver;
import swt6.spring.worklog.client.util.AppEntity;

@ShellComponent
public class ShellCommands {
    private final UtilDriver utilDriver;
    private final IssueDriver issueDriver;
    private final ProjectDriver projectDriver;
    private final EmployeeDriver employeeDriver;

    public ShellCommands(UtilDriver validateDriver,
                         IssueDriver issueDriver,
                         ProjectDriver projectDriver,
                         EmployeeDriver employeeDriver) {
        this.utilDriver = validateDriver;
        this.issueDriver = issueDriver;
        this.projectDriver = projectDriver;
        this.employeeDriver = employeeDriver;
    }

    @ShellMethod("Create an instance of an object")
    public String create(@ShellOption String kind) {
        AppEntity e = utilDriver.validateCreateParam(kind);

        if (e == null)
            return String.format("'%s' is not a supported entity", kind);

        if (e == AppEntity.project) {
            return projectDriver.createCommand();
        }
        else {
            issueDriver.createCommand();
        }

        return kind;
    }

    @ShellMethod("List employees or issues")
    public String list(@ShellOption String kind, @ShellOption(defaultValue = "") String option) {
        AppEntity e = utilDriver.validateListParam(kind);

        if (e == null)
            return String.format("'%s' is not a supported entity", kind);

        StringBuffer sb = new StringBuffer();

        if (e == AppEntity.employee) {
            return employeeDriver.listEmployeeCommand(option);
        }

        return "adf";
    }

    @ShellMethod("Update projects or issues")
    public String update(@ShellOption String kind, @ShellOption(defaultValue = "") String option) {
        AppEntity e = utilDriver.validateUpdateParam(kind);

        if (e == null)
            return String.format("'%s' is not a supported entity", kind);

        if (e == AppEntity.project) {
            return projectDriver.updateCommand(option);
        }

        return "todo";
    }
}
