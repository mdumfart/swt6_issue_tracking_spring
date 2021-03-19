package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Issue;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.domain.Project;

import java.util.List;

@Repository
public interface LogbookEntryDao extends JpaRepository<LogbookEntry, Long> {
    @Query("select lbe from LogbookEntry lbe where lbe.issue.project = :project")
    List<LogbookEntry> findByProject(@Param("project") Project project);

    @Query("select lbe from LogbookEntry lbe where lbe.issue.project = :project AND lbe.employee = :employee")
    List<LogbookEntry> findByProjectAndEmployee(@Param("project") Project project, @Param("employee")Employee employee);

    @Query("select lbe from LogbookEntry lbe where lbe.employee = :employee")
    List<LogbookEntry> findByEmployee(@Param("employee") Employee employee);

    @Query("select lbe from LogbookEntry lbe where lbe.issue = :issue")
    List<LogbookEntry> findByIssue(@Param("issue")Issue issue);
}
