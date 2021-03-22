package swt6.spring.worklog.client.beans;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import swt6.spring.worklog.client.util.AppEntity;
import swt6.spring.worklog.domain.Project;

import java.util.Arrays;
import java.util.List;

@Component
public class UtilDriver {
    private static final List<String> issueAliases = Arrays.asList("issue", "iss", "i");
    private static final List<String> projectAliases = Arrays.asList("project", "proj", "p");
    private static final List<String> employeeAliases = Arrays.asList("employee", "empl", "e");
    private LineReader lineReader;

    @Autowired
    @Lazy
    public void setLineReader(LineReader lineReader) {
        this.lineReader = lineReader;
    }

    public AppEntity validateCreateParam(String param) {
        if (projectAliases.contains(param)) return AppEntity.project;
        if (issueAliases.contains(param)) return AppEntity.issue;
        return null;
    }

    public AppEntity validateListParam(String param) {
        if (employeeAliases.contains(param)) return AppEntity.employee;
        if (issueAliases.contains(param)) return AppEntity.issue;
        if (projectAliases.contains(param)) return AppEntity.project;
        return null;
    }

    public AppEntity validateUpdateParam(String param) {
        return validateCreateParam(param);
    }

    public <T> String listCollection(String description, List<T> entities) {
        StringBuffer sb = new StringBuffer();

        sb.append(String.format("%s%n", description));

        for (T e : entities) {
            sb.append(String.format("%s%n", e.toString()));
        }

        return sb.toString();
    }

    public int parseInputToId(String hint) {
        int id = -1;

        do  {
            String input = lineReader.readLine(String.format("%s> ", hint));

            try {
                id = Integer.parseInt(input);
            }
            catch (Exception ex) {
                id = -1;
            }
        } while (id < 0);

        return id;
    }

    public String getConsoleInput(String hint) {
        return lineReader.readLine(String.format("%s> ", hint));
    }

    public double parseInputToDouble(String hint) {
        double d;

        do  {
            String input = lineReader.readLine(String.format("%s> ", hint));

            try {
                d = Double.parseDouble(input);
            }
            catch (Exception ex) {
                d = -1.0;
            }
        } while (d < 0);

        return d;
    }
}
