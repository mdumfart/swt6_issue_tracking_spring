package swt6.spring.worklog.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.shell.InputProvider;
import org.springframework.shell.Shell;
import swt6.spring.worklog.client.shell.ShellConfig;
import swt6.spring.worklog.client.viewmodels.UtilDriver;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Issue;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.domain.Project;
import swt6.spring.worklog.domain.util.IssuePriority;
import swt6.spring.worklog.domain.util.IssueState;
import swt6.spring.worklog.services.EmployeeService;
import swt6.spring.worklog.services.IssueService;
import swt6.spring.worklog.services.LogbookEntryService;
import swt6.spring.worklog.services.ProjectService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class App {
    private static UtilDriver utilDriver;

    public static void main(String[] args) throws IOException {
        try (AbstractApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class, ShellConfig.class)) {

            utilDriver = context.getBean(UtilDriver.class);

            utilDriver.setupApplication();

            Shell shell = context.getBean(Shell.class);
            shell.run(context.getBean(InputProvider.class));
        }
    }

}
