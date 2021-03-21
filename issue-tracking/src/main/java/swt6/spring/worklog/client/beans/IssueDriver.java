package swt6.spring.worklog.client.beans;

import org.springframework.stereotype.Component;
import swt6.spring.worklog.domain.Issue;
import swt6.spring.worklog.domain.Project;

@Component
public class IssueDriver {
    public Issue createCommand() {
        System.out.println("Create a new issue:");
        return null;
    }
}
