package swt6.spring.worklog.client.viewmodels;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import swt6.spring.worklog.client.util.AppEntity;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class UtilDriver {
    private static final List<String> issueAliases = Arrays.asList("issue", "iss", "i");
    private static final List<String> projectAliases = Arrays.asList("project", "proj", "p");
    private static final List<String> employeeAliases = Arrays.asList("employee", "empl", "e");
    private static LineReader lineReader;
    private EmployeeService employeeService;
    private ProjectService projectService;
    private IssueService issueService;
    private LogbookEntryService logbookEntryService;

    @Autowired
    @Lazy
    public void setLineReader(LineReader lineReader) {
        this.lineReader = lineReader;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Autowired
    public void setIssueService(IssueService issueService) {
        this.issueService = issueService;
    }

    @Autowired
    public void setLogbookEntryService(LogbookEntryService logbookEntryService) {
        this.logbookEntryService = logbookEntryService;
    }

    public AppEntity validateCreateParam(String param) {
        if (projectAliases.contains(param)) return AppEntity.project;
        if (issueAliases.contains(param)) return AppEntity.issue;
        return null;
    }

    public AppEntity validateListParam(String param) {
        if (employeeAliases.contains(param)) return AppEntity.employee;
        if (issueAliases.contains(param)) return AppEntity.issue;
        if (projectAliases.contains(param)) return AppEntity.project;
        return null;
    }

    public AppEntity validateUpdateParam(String param) {
        return validateCreateParam(param);
    }

    public <T> String listCollection(String description, List<T> entities) {
        StringBuffer sb = new StringBuffer();

        sb.append(String.format("%s%n", description));

        for (T e : entities) {
            sb.append(String.format("%s%n", e.toString()));
        }

        return sb.toString();
    }

    public int parseInputToId(String hint) {
        int id = -1;

        do  {
            String input = lineReader.readLine(String.format("%s> ", hint));

            try {
                id = Integer.parseInt(input);
            }
            catch (Exception ex) {
                id = -1;
            }
        } while (id < 0);

        return id;
    }

    public String getConsoleInput(String hint) {
        return lineReader.readLine(String.format("%s> ", hint));
    }

    public double parseInputToDouble(String hint) {
        double d;

        do  {
            String input = lineReader.readLine(String.format("%s> ", hint));

            try {
                d = Double.parseDouble(input);
            }
            catch (Exception ex) {
                d = -1.0;
            }
        } while (d < 0);

        return d;
    }

    public void setupApplication() {
        // CREATE EMPLOYEES ===================================================

        Employee empl1 = new Employee("Michael", "Dumfart", LocalDate.of(1997, 5, 14));
        Employee empl2 = new Employee("Peter", "Test", LocalDate.of(1960, 8, 22));
        Employee empl3 = new Employee("Hans", "Zimmer", LocalDate.of(1975, 1, 1));

        employeeService.save(empl1);
        employeeService.save(empl2);
        employeeService.save(empl3);

        // CREATE PROJECTS ====================================================

        Project project1 = new Project("Apollo");
        project1.addEmployee(empl1);
        project1.addEmployee(empl2);

        projectService.save(project1);

        // CREATE ISSUES ======================================================

        Issue issue1 = new Issue("Implement Daos", IssueState.open, IssuePriority.high, project1);
        issue1.setEmployee(empl1);
        issue1.setEstimatedTime(7.5);

        Issue issue2 = new Issue("Fix some bugs", IssueState.open, IssuePriority.high, project1);
        issue2.setEmployee(empl1);
        issue2.setEstimatedTime(3.5);

        Issue issue3 = new Issue("Create Movie Detail Page", IssueState.open, IssuePriority.low, project1);
        issue3.setEmployee(empl2);
        issue3.setEstimatedTime(5);

        issueService.create(issue1);
        issueService.create(issue2);
        issueService.create(issue3);

        // CREATE LOGBOOKENTRIES ==============================================

        LogbookEntry lbe1 = new LogbookEntry(
                "Implement Movie Daos",
                LocalDateTime.of(2021, 3, 22, 8, 0),
                LocalDateTime.of(2021, 3, 22, 13, 30));
        lbe1.setIssue(issue1);
        lbe1.setEmployee(empl1);

        LogbookEntry lbe2 = new LogbookEntry(
                "Fix some strange bug",
                LocalDateTime.of(2021, 3, 22, 14, 0),
                LocalDateTime.of(2021, 3, 22, 16, 30));
        lbe2.setIssue(issue2);
        lbe2.setEmployee(empl1);

        LogbookEntry lbe3 = new LogbookEntry(
                "Fix some strange frontend bug",
                LocalDateTime.of(2021, 3, 22, 14, 0),
                LocalDateTime.of(2021, 3, 22, 16, 30));
        lbe3.setIssue(issue3);
        lbe3.setEmployee(empl2);

        logbookEntryService.save(lbe1);
        logbookEntryService.save(lbe2);
        logbookEntryService.save(lbe3);
    }
}
