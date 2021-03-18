package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.spring.worklog.domain.Employee;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {
}