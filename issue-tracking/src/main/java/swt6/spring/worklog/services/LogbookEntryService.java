package swt6.spring.worklog.services;

import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Issue;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.domain.Project;

import java.util.List;
import java.util.Optional;

public interface LogbookEntryService {
    Optional<LogbookEntry> findById(long id);
    List<LogbookEntry> findAll();
    LogbookEntry save(LogbookEntry logbookEntry);
    void delete(LogbookEntry logbookEntry);
    List<LogbookEntry> findByProject(Project project);
    List<LogbookEntry> findByProjectAndEmployee(Project project, Employee employee);
    List<LogbookEntry> findByEmployee(Employee employee);
    List<LogbookEntry> findByIssue(Issue issue);
}
