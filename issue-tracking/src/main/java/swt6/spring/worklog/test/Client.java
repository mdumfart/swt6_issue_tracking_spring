package swt6.spring.worklog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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

            EmployeeLogic employeeLogic = factory.getBean(EmployeeLogic.class);

            Employee employee1 = new Employee("Michael", "Dumfart", LocalDate.of(1997, 5, 14));

            employee1 = employeeLogic.save(employee1);

            System.out.println(employee1);

            ProjectLogic projectLogic = factory.getBean(ProjectLogic.class);
            IssueLogic issueLogic = factory.getBean(IssueLogic.class);

            Project project1 = new Project("TestProject1");

            projectLogic.save(project1);

            Issue issue1 = new Issue("testIssue1", IssueState.nev, IssuePriority.low, 0.0, project1);

            issueLogic.create(issue1);
        }
    }

    public static void main(String[] args) {
        test();
    }
}
