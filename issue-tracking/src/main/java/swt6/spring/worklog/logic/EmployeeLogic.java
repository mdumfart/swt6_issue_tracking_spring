package swt6.spring.worklog.logic;

import org.springframework.stereotype.Component;
import swt6.spring.worklog.domain.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeLogic {
    Optional<Employee> findById(long id);
    List<Employee> findAll();
    Employee save(Employee employee);
}
