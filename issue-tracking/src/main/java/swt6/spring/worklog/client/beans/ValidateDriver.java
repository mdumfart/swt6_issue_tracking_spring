package swt6.spring.worklog.client.beans;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ValidateDriver {
    private static final List<String> entities = Arrays.asList("address", "employee", "issue", "logbookEntry", "project");
    private static final List<String> entitiesAlternative = Arrays.asList("addr", "empl", "iss", "lbe", "proj");

    public boolean validateCreateParam(String param) {
        return entities.contains(param) || entitiesAlternative.contains(param);
    }
}
