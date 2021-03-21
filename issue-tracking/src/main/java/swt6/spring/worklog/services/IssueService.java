package swt6.spring.worklog.services;

import swt6.spring.worklog.domain.Issue;

import java.util.List;
import java.util.Optional;

public interface IssueService {
    Optional<Issue> findById(long id);
    List<Issue> findAll();
    Issue create(Issue issue);
    void update(Issue issue);
    void updateExpendedTime(Issue issue);
}
