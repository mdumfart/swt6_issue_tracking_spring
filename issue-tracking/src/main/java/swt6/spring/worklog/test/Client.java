package swt6.spring.worklog.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Issue;
import swt6.spring.worklog.domain.Project;
import swt6.spring.worklog.domain.util.IssuePriority;
import swt6.spring.worklog.domain.util.IssueState;
import swt6.spring.worklog.logic.*;

import static swt6.util.PrintUtil.printSeparator;
import static swt6.util.PrintUtil.printTitle;

import java.time.LocalDate;

public class Client {
    private static void test() {
        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
                "swt6/spring/worklog/applicationContext.xml")) {

            EmployeeService employeeService = factory.getBean(EmployeeService.class);

            Employee employee1 = new Employee("Michael", "Dumfart", LocalDate.of(1997, 5, 14));

            employee1 = employeeService.save(employee1);

            System.out.println(employee1);

            ProjectService projectService = factory.getBean(ProjectService.class);
            IssueService issueService = factory.getBean(IssueService.class);

            Project project1 = new Project("TestProject1");

            projectService.save(project1);


            Issue issue1 = new Issue("testIssue1", IssueState.nev, IssuePriority.low, 0.0, project1);
            issueService.create(issue1);
        }
    }

    public static void main(String[] args) {
        test();
    }
}
