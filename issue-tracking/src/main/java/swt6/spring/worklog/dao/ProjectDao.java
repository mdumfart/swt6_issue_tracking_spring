package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Project;

import java.util.Set;

@Repository
public interface ProjectDao extends JpaRepository<Project, Long> {
}
