package swt6.spring.worklog.logic;

import swt6.spring.worklog.domain.Issue;

public interface IssueLogic {
    Issue create(Issue issue);
    void update(Issue issue);

}
