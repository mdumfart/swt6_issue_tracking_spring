package swt6.spring.worklog.logic;

import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Project;

import java.util.Optional;
import java.util.Set;

public interface ProjectLogic {
    Optional<Project> findById(long id);
    Set<Project> findAll();
    Project save(Project project);

    Project assignEmployeeToProject(Employee employee, Project project);
    Project removeEmployeeFromProject(Employee employee, Project project);

}
