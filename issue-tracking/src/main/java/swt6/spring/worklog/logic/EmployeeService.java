package swt6.spring.worklog.logic;

import org.springframework.stereotype.Component;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Project;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Optional<Employee> findById(long id);
    List<Employee> findAll();
    Employee save(Employee employee);
    List<Employee> findByProject(Project project);
}
