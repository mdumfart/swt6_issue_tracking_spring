package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Project;

import java.util.List;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {
    @Query("select e from Project p JOIN p.employees e where p = :project")
    List<Employee> findByProject(@Param("project")Project project);
}