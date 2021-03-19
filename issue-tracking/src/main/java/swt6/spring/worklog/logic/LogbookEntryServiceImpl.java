package swt6.spring.worklog.logic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.LogbookEntryDao;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Issue;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.domain.Project;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LogbookEntryServiceImpl implements LogbookEntryService {
    private LogbookEntryDao logbookEntryDao;

    public LogbookEntryServiceImpl(LogbookEntryDao logbookEntryDao) {
        this.logbookEntryDao = logbookEntryDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LogbookEntry> findById(long id) {
        return logbookEntryDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LogbookEntry> findAll() {
        return logbookEntryDao.findAll();
    }

    @Override
    public LogbookEntry save(LogbookEntry logbookEntry) {
        return logbookEntryDao.save(logbookEntry);
    }

    @Override
    public void delete(LogbookEntry logbookEntry) {
        logbookEntryDao.delete(logbookEntry);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LogbookEntry> findByProject(Project project) {
        return logbookEntryDao.findByProject(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LogbookEntry> findByProjectAndEmployee(Project project, Employee employee) {
        return logbookEntryDao.findByProjectAndEmployee(project, employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LogbookEntry> findByEmployee(Employee employee) {
        return logbookEntryDao.findByEmployee(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LogbookEntry> findByIssue(Issue issue) {
        return logbookEntryDao.findByIssue(issue);
    }
}