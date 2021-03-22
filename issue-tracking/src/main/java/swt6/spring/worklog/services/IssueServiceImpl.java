package swt6.spring.worklog.services;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.IssueDao;
import swt6.spring.worklog.domain.Issue;
import swt6.spring.worklog.domain.LogbookEntry;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IssueServiceImpl implements IssueService {
    private final IssueDao issueDao;
    private final LogbookEntryService logbookEntryService;

    public IssueServiceImpl(IssueDao issueDao, LogbookEntryService logbookEntryService) {
        this.issueDao = issueDao;
        this.logbookEntryService = logbookEntryService;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Issue> findById(long id) {
        return issueDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Issue> findAll() {
        return issueDao.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Issue create(Issue issue) throws IllegalArgumentException {
        if (issue.getProject() == null)
            throw new IllegalArgumentException("No project provided for issue");

        if (issue.getEmployee() != null && !issue.getProject().getEmployees().contains(issue.getEmployee()))
            throw new IllegalArgumentException("Assigned employee to issue must be involved in project");

        if (issue.getEmployee() != null && issue.getEstimatedTime() <= 0)
            throw new IllegalArgumentException("Estimated time must be provided, when an employee is assigned");

        return issueDao.save(issue);
    }

    @Override
    public Issue update(Issue issue) {
        Optional<Issue> persistedIssueOptional = issueDao.findById(issue.getId());

        // Check if issue does exist
        if (persistedIssueOptional.isEmpty())
            throw new IllegalArgumentException("Issue does not exist");

        Issue persistedIssue = persistedIssueOptional.get();

        // Check if new issue has a project assigned
        if (issue.getProject() == null)
            throw new IllegalArgumentException("Issue always needs a valid project assigned to it");

        // Check if an employee is assigned, if yes check if estimated time is provided
        if (issue.getEmployee() != null && issue.getEstimatedTime() <= 0)
            throw new IllegalArgumentException("Estimated time needs to be provided when assigning an employee to an issue");

        // Check if new issue assigns a new employee when one is already assigned
        if (persistedIssue.getEmployee() != null && !persistedIssue.getEmployee().equals(issue.getEmployee()))
            throw new IllegalArgumentException("Cannot change assigned employee on issue");

        // Check if employee belongs to project of issue when assigning a new employee
        if (persistedIssue.getEmployee() == null && issue.getEmployee() != null) {
            if (issue.getProject().getEmployees().stream().noneMatch(e -> e.equals(issue.getEmployee()))) {
                throw new IllegalArgumentException("Assigned employee to issue must be involved in project");
            }
        }

        return issueDao.save(issue);
    }

    @Override
    public void updateExpendedTime(Issue issue) {
        double time = 0.0;

        for (LogbookEntry lbe : logbookEntryService.findByIssue(issue)) {
            time += lbe.getTimeSpent();
        }

        issue.setExpendedTime(time);

        issueDao.save(issue);
    }
}
