package swt6.spring.worklog.logic;

import swt6.spring.worklog.dao.IssueDao;
import swt6.spring.worklog.domain.Issue;

public class IssueLogicImpl implements IssueLogic {
    private IssueDao issueDao;

    

    @Override
    public Issue create(Issue issue) throws IllegalArgumentException {
        if (issue.getProject() == null)
            throw new IllegalArgumentException("No project provided for issue");


    }

    @Override
    public void update(Issue issue) {

    }
}
