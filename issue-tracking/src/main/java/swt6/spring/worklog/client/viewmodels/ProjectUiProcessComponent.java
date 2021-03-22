package swt6.spring.worklog.client.viewmodels;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Issue;
import swt6.spring.worklog.domain.Project;
import swt6.spring.worklog.services.EmployeeService;
import swt6.spring.worklog.services.ProjectService;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProjectUiProcessComponent implements ProjectUiProcessFacade {
    private LineReader lineReader;
    private ProjectService projectService;
    private EmployeeService employeeService;
    private UtilDriver utilDriver;

    @Autowired
    @Lazy
    public void setLineReader(LineReader lineReader) {
        this.lineReader = lineReader;
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
        System.out.println("Create a new project:");
        String value = lineReader.readLine("Enter title> ");

        Project p = projectService.save(new Project(value));

        return p.toString();
    }

    @Override
    public String updateCommand(String option) {
        int projectId;

        if (option.isEmpty()) {
            System.out.println();
            printAllProjects();
            System.out.println();

            do  {
                String input = lineReader.readLine("Enter projectId> ");

                try {
                    projectId = Integer.parseInt(input);
                }
                catch (Exception ex) {
                    projectId = -1;
                }
            } while (projectId < 0);
        }
        else {
            try {
                projectId = Integer.parseInt(option);
            }
            catch (Exception ex) {
                return String.format("'%s' is not a valid projectId%n", option);
            }
        }

        Optional<Project> optionalP = projectService.findById(projectId);

        if (optionalP.isEmpty()) return String.format("Project with id [%s] not found%n", projectId);

        return updateProject(optionalP.get());
    }

    @Override
    public void printAllProjects() {
        List<Project> projectList = projectService.findAll();

        System.out.println("All available projects:");

        for(Project p : projectList) {
            System.out.printf("  %s%n", p.toString());
        }
    }

    private String updateProject(Project project) {
        System.out.printf("Update project %s%n", project.toString());
        System.out.println("  1: Change name");
        System.out.println("  2: Add employee");
        System.out.println("  3: Remove employee");

        boolean inputSuccess = false;

        while (!inputSuccess) {
            String input = lineReader.readLine("Enter option> ");
            inputSuccess = true;

            switch (input) {
                case "1": return changeProjectTitle(project);
                case "2": return addEmployeeToProject(project);
                case "3": return removeEmployeeFromProject(project);
                default: inputSuccess = false;
            }
        }

        return "Something went wrong";
    }

    private String changeProjectTitle(Project project) {
        String input = lineReader.readLine("Enter new project name> ");
        project.setName(input);
        project = projectService.save(project);

        StringBuffer sb = new StringBuffer();
        sb.append(String.format("Project title changed:%n"));
        sb.append(project.toString());
        return sb.toString();
    }

    private String addEmployeeToProject(Project project) {
        List<Employee> employees = employeeService.findAll();

        if (employees.isEmpty()) {
            return "There are no employees to add";
        }

        for (Employee e : project.getEmployees()) {
            employees.remove(e);
        }

        System.out.printf("The following employees are available to add to %s%n", project.toString());
        for (Employee e : employees) {
            System.out.printf("  %s%n", e.toString());
        }

        int employeeId;
        Employee employee = null;
        do {
            String input = lineReader.readLine("Enter employeeId to add> ");

            try {
                employeeId = Integer.parseInt(input);
            } catch (Exception ex) {
                employeeId = -1;
            }

            if (employeeId != -1)
                employee = employeeService.findById(employeeId).get();

        } while (employee == null);

        project = projectService.assignEmployeeToProject(employee, project);

        return "success";
    }

    private String removeEmployeeFromProject(Project project) {
        List<Employee> employees = employeeService.findByProject(project);

        if (employees.isEmpty()) {
            return "There are no employees to remove";
        }

        StringBuffer sb = new StringBuffer();

        sb.append(String.format("Employees assigned to %s%n", project.toString()));

        for (Employee employee : employees) {
            sb.append(String.format("  %s%n", employee.toString()));
        }

        System.out.println(sb.toString());

        int employeeId;
        Employee employee = null;
        do {
            String input = lineReader.readLine("Enter employeeId to remove> ");

            try {
                employeeId = Integer.parseInt(input);
            } catch (Exception ex) {
                employeeId = -1;
            }

            if (employeeId != -1)
                employee = employeeService.findById(employeeId).get();

        } while (employee == null);

        project = projectService.removeEmployeeFromProject(employee, project);

        return "success";
    }

    @Override
    public String listProjects(String option) {
        if (option.isEmpty()) {
            List<Project> projects = projectService.findAll();

            return utilDriver.listCollection("The following projects are available:", projects);
        }
        else if (option.equals("time")) {
            return listTime();
        }
        else {
            return String.format("'%s' is not recognized as a valid parameter");
        }
    }

    private String listTime() {
        Project project = null;

        System.out.println();
        printAllProjects();
        System.out.println();

        do  {
            String input = lineReader.readLine("Enter projectId> ");

            try {
                int projectId = Integer.parseInt(input);
                Optional<Project> optionalP = projectService.findById(projectId);
                project = optionalP.get();
            }
            catch (Exception ex) {
                project = null;
            }
        } while (project == null);

        List<Employee> employees = project.getEmployees();

        StringBuffer sb = new StringBuffer();

        sb.append(String.format("Working times for project %s%n", project.toString()));

        for (Employee employee : employees) {
            double estimatedTimeSum = getEstimatedTimeSumOfProjectByEmployee(project, employee);

            sb.append(String.format("  %s%n", employee.toString()));
            sb.append(
                    String.format("    Time already invested: %.2fh%n",
                    projectService.getTimeInvestedInProjectByEmployee(project, employee)));

            sb.append(
                    String.format("    Time to invest: %.2fh%n",
                    projectService.getTimeToInvestInProjectByEmployee(project, employee)));

            sb.append(String.format("    Estimated time sum: %.2fh%n", estimatedTimeSum));
        }

        return sb.toString();
    }

    private double getEstimatedTimeSumOfProjectByEmployee(Project project, Employee employee) {
        double timeSum = 0.0;

        for (Issue issue : project.getIssues().stream().filter(
                issue -> issue.getEmployee().getId() == employee.getId()).collect(Collectors.toList())) {
            timeSum += issue.getEstimatedTime();
        }

        return timeSum;
    }
}
