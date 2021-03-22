package swt6.spring.worklog.client.beans;

import org.springframework.stereotype.Component;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Project;
import swt6.spring.worklog.services.EmployeeService;
import swt6.spring.worklog.services.ProjectService;

import java.util.List;
import java.util.Optional;

@Component
public class EmployeeDriver {
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    public EmployeeDriver(EmployeeService employeeService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    public String listEmployeeCommand(String option) {
        StringBuffer sb = new StringBuffer();

        if (option.isEmpty()) {
            List<Employee> employeeList = employeeService.findAll();

            sb.append(String.format("All available employees:%n"));

            for (Employee employee : employeeList) {
                sb.append(String.format("  %s%n", employee.toString()));
            }
        }
        else {
            int projectId;

            try {
                projectId = Integer.parseInt(option);
            }
            catch (Exception ex) {
                return String.format("'%s' is not a valid projectId", option);
            }

            Optional<Project> optionalP = projectService.findById(projectId);
            if (optionalP.isEmpty()) return String.format("Project with id [%s] not found", projectId);

            List<Employee> employeeList = employeeService.findByProject(optionalP.get());

            sb.append(String.format("Employees assigned to %s%n", optionalP.get().toString()));

            for (Employee employee : employeeList) {
                sb.append(String.format("  %s%n", employee.toString()));
            }
        }

        return sb.toString();
    }
}
