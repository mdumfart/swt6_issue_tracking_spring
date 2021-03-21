package swt6.spring.worklog.client.beans;

import org.springframework.stereotype.Component;
import swt6.spring.worklog.client.util.AppEntity;

@Component
public class UtilDriver {
    private static final String issue = "issue";
    private static final String issueAlt = "iss";
    private static final String project = "project";
    private static final String projectAlt = "proj";
    private static final String employee = "employee";
    private static final String employeeAlt = "empl";

    public AppEntity validateCreateParam(String param) {
        if (param.equals(project) || param.equals(projectAlt)) return AppEntity.project;
        if (param.equals(issue) || param.equals(issueAlt)) return AppEntity.issue;
        return null;
    }

    public AppEntity validateListParam(String param) {
        if (param.equals(employee) || param.equals(employeeAlt)) return AppEntity.employee;
        if (param.equals(issue) || param.equals(issueAlt)) return AppEntity.issue;
        return null;
    }

    public AppEntity validateUpdateParam(String param) {
        return validateCreateParam(param);
    }
}
