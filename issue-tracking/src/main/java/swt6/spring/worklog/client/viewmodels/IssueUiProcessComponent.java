package swt6.spring.worklog.client.viewmodels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Issue;
import swt6.spring.worklog.domain.Project;
import swt6.spring.worklog.domain.util.IssuePriority;
import swt6.spring.worklog.domain.util.IssueState;
import swt6.spring.worklog.services.EmployeeService;
import swt6.spring.worklog.services.IssueService;
import swt6.spring.worklog.services.ProjectService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class IssueUiProcessComponent implements IssueUiProcessFacade {
    private IssueService issueService;
    private ProjectService projectService;
    private EmployeeService employeeService;
    private UtilDriver utilDriver;

    private final String GROUPED_FLAG = "-g";

    @Autowired
    public void setIssueService(IssueService issueService) {
        this.issueService = issueService;
    }

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setUtilDriver(UtilDriver utilDriver) {
        this.utilDriver = utilDriver;
    }

    @Override
    public String createCommand() {
        System.out.println("Create a new issue:");

        Project project = selectProject();
        if (project == null) {
            return "No projects to add issue to available";
        }

        String name = utilDriver.getConsoleInput("Enter issue name");
        IssueState state = selectIssueState(name);
        IssuePriority priority = selectIssuePriority(name);

        Issue issue = new Issue(name, state, priority, project);

        try {
            issue = issueService.create(issue);
        } catch (Exception ex) {
            return ex.toString();
        }

        StringBuffer sb = new StringBuffer();
        sb.append(String.format("Issue created for project %s%n", project));

        sb.append(String.format("  %s", issue.toString()));

        return sb.toString();
    }

    private Project selectProject() {
        List<Project> projects = projectService.findAll();

        if (projects.isEmpty())
            return null;

        System.out.println(utilDriver.listCollection("The following projects are available:", projects));

        int id = -1;
        Optional<Project> optionalP = null;
        do {
            id = utilDriver.parseInputToId("Enter projectId");
            optionalP = projectService.findById(id);
        } while (optionalP.isEmpty());

        return optionalP.get();
    }
    private IssueState selectIssueState(String issueName) {
        System.out.printf("Select initial state for issue %s%n", issueName);
        System.out.println("  1: new");
        System.out.println("  2: open");
        System.out.println("  3: resolved");
        System.out.println("  4: closed");
        System.out.println("  5: rejected");

        boolean inputSuccess = false;

        while (!inputSuccess) {
            String input = utilDriver.getConsoleInput("Enter option");
            inputSuccess = true;

            switch (input) {
                case "1": return IssueState.nev;
                case "2": return IssueState.open;
                case "3": return IssueState.resolved;
                case "4": return IssueState.closed;
                case "5": return IssueState.rejected;
                default: inputSuccess = false;
            }
        }

        return IssueState.nev;
    }

    private IssuePriority selectIssuePriority(String issueName) {
        System.out.printf("Select priority for issue %s%n", issueName);
        System.out.println("  1: low");
        System.out.println("  2: high");
        boolean inputSuccess = false;

        while (!inputSuccess) {
            String input = utilDriver.getConsoleInput("Enter option");
            inputSuccess = true;

            switch (input) {
                case "1": return IssuePriority.low;
                case "2": return IssuePriority.high;
                default: inputSuccess = false;
            }
        }

        return IssuePriority.low;
    }


    @Override
    public String listIssues(String option1, String option2, String option3) {
        if (option1.isEmpty()) return "Provide a project to list issues";

        Project project;

        try {
            int projectId = Integer.parseInt(option1);
            Optional<Project> optionalP = projectService.findById(projectId);

            if (optionalP.isEmpty()) throw new IllegalArgumentException();
            project = optionalP.get();
        } catch (Exception ex) {
            return "Please provide a valid projectId";
        }

        boolean grouped = option2.equals(GROUPED_FLAG) || option3.equals(GROUPED_FLAG);

        IssueState filterState;

        if (!option2.equals(GROUPED_FLAG)) {
            filterState = resolveFilterParam(option2);
        }
        else {
            filterState = resolveFilterParam(option3);
        }

        if (!grouped) return listIssuesNotGrouped(project, filterState);

        return listIssuesGrouped(project, filterState);
    }

    private String listIssuesNotGrouped(Project project, IssueState filterState) {
        List<Issue> issues = getFilteredIssuesByState(project, filterState);

        if (filterState != null) {
            return utilDriver.listCollection(
                    String.format("Issues of project %s, filtered by %s", project.toString(), filterState.toString()),
                    issues);
        }
        else {
            return utilDriver.listCollection(
                    String.format("Issues of project %s", project.toString()),
                    issues);
        }
    }

    private String listIssuesGrouped(Project project, IssueState filterState) {
        List<Issue> issues = getFilteredIssuesByState(project, filterState);
        List<Employee> employees = project.getEmployees();

        StringBuffer sb = new StringBuffer();

        if (filterState != null) {
            sb.append(String.format("Issues of project %s, filtered by %s, grouped by Employees:%n",
                            project.toString(), filterState.toString()));

            for (Employee e : employees) {
                sb.append(utilDriver.listCollection(
                        String.format("Issues of %s %s:", e.getFirstName(), e.getLastName()),
                        getFilteredIssuesByEmployee(project, e)));

                sb.append(String.format("%n"));

            }
        }
        else {
            sb.append(String.format("Issues of project %s, grouped by Employees:%n", project.toString()));

            for (Employee e : employees) {
                sb.append(utilDriver.listCollection(
                        String.format("Issues of %s %s:", e.getFirstName(), e.getLastName()),
                        getFilteredIssuesByEmployee(project, e)));

                sb.append(String.format("%n"));
            }
        }

        return sb.toString();
    }

    private List<Issue> getFilteredIssuesByState(Project project, IssueState state) {
        if (state == null) return project.getIssues();

        return project.getIssues().stream().filter(issue -> issue.getState() == state).collect(Collectors.toList());
    }

    private List<Issue> getFilteredIssuesByEmployee(Project project, Employee employee) {
        return project.getIssues().stream().filter(issue -> issue.getEmployee() == employee).collect(Collectors.toList());
    }

    private IssueState resolveFilterParam(String filterString) {
        switch (filterString){
            case "new": return IssueState.nev;
            case "open": return IssueState.open;
            case "resolved": return IssueState.resolved;
            case "closed": return IssueState.closed;
            case "rejected": return IssueState.rejected;
            default: return null;
        }
    }

    @Override
    public String updateCommand(String option) {
        Optional<Issue> optionalI;

        if (option.isEmpty()) {
            System.out.println();
            System.out.println(utilDriver.listCollection("Available issues:", issueService.findAll()));

            do  {
                int issueId = utilDriver.parseInputToId("Enter issueId");
                optionalI = issueService.findById(issueId);

                if (optionalI.isEmpty()) System.out.printf("Issue with id [%s] not found", issueId);

            } while (optionalI.isEmpty());
        }
        else {
            try {
                int issueId = Integer.parseInt(option);

                if (issueId < 0) throw new IllegalArgumentException();
                optionalI = issueService.findById(issueId);

                if (optionalI.isEmpty()) {
                    return String.format("Issue with id [%s] not found", issueId);
                }

            }
            catch (Exception ex) {
                return String.format("'%s' is not a valid projectId%n", option);
            }
        }

        return updateIssue(optionalI.get());
    }

    private String updateIssue(Issue issue) {
        System.out.println("Updating:");
        System.out.println(issue.toString());

        System.out.println("  1: Change project");
        System.out.println("  2: Assign employee");
        System.out.println("  3: Change issue state");
        System.out.println("  4: Change estimated time");

        boolean inputSuccess = false;

        while (!inputSuccess) {
            String input = utilDriver.getConsoleInput("Enter option");
            inputSuccess = true;

            switch (input) {
                case "1": return changeProject(issue);
                case "2": return assignEmployee(issue);
                case "3": return changeIssueState(issue);
                case "4": return changeEstimatedTime(issue);
                default: inputSuccess = false;
            }
        }

        return "Something went wrong";
    }

    private String changeProject(Issue issue) {
        if (issue.getEmployee() != null) return "Cannot change project since an employee is already assigned";

        List<Project> projects = projectService.findAll();
        projects.remove(issue.getProject());

        System.out.println(utilDriver.listCollection("Available projects", projects));

        Optional<Project> optionalP;

        do  {
            int projectId = utilDriver.parseInputToId("Enter projectId");
            optionalP = projectService.findById(projectId);

            if (optionalP.isEmpty()) System.out.printf("Project with id [%s] not found", projectId);

        } while (optionalP.isEmpty());

        issue.setProject(optionalP.get());

        try {
            issueService.update(issue);
        } catch (Exception ex) {
            return ex.toString();
        }

        StringBuffer sb = new StringBuffer();
        sb.append(String.format("Issue Updated:%n"));
        sb.append(issue.toString());

        return sb.toString();
    }

    private String assignEmployee(Issue issue) {
        List<Employee> employees = issue.getProject().getEmployees();

        if (issue.getEmployee() != null) return "Issue has already an employee assigned";

        if (employees.isEmpty()) return "No employees available";

        System.out.println(utilDriver.listCollection("Available employees to assign to issue", employees));

        Optional<Employee> optionalE;

        do  {
            int employeeId = utilDriver.parseInputToId("Enter employeeId");
            optionalE = findEmployeeInList(employeeId, employees);

            if (optionalE.isEmpty()) System.out.printf("Employee with id [%s] not acceptable", employeeId);

        } while (optionalE.isEmpty());

        issue.setEmployee(optionalE.get());

        String resultString = "";

        try {
            issue = issueService.update(issue);
        } catch (Exception e) {
            resultString = e.getMessage();
        }

        if (resultString.isEmpty()) {
            return "Employee assigned successfully";
        }
        else {
            return resultString;
        }

    }

    private Optional<Employee> findEmployeeInList(int employeeId, List<Employee> employees) {
        return employees.stream().filter(employee -> employee.getId() == employeeId).findFirst();
    }

    private String changeIssueState(Issue issue) {
        IssueState state = selectIssueState(issue.getName());

        issue.setState(state);
        issueService.update(issue);

        return String.format("Issue state updated to %s", state.toString());
    }

    private String changeEstimatedTime(Issue issue) {
        double estimatedTime = utilDriver.parseInputToDouble("Enter estimated time in h");

        issue.setEstimatedTime(estimatedTime);
        issueService.update(issue);

        return String.format("Issue estimated time updated to %f", estimatedTime);
    }
}
