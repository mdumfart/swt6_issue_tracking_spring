package swt6.spring.worklog.logic;

import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    Optional<Project> findById(long id);
    List<Project> findAll();
    Project save(Project project);

    Project assignEmployeeToProject(Employee employee, Project project);
    Project removeEmployeeFromProject(Employee employee, Project project);

    double getTimeInvestedInProjectByEmployee(Project project, Employee employee);
    double getTimeToInvestInProjectByEmployee(Project project, Employee employee);
}
