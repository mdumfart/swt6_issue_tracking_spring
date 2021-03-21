package swt6.spring.worklog.client.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import swt6.spring.worklog.client.beans.ValidateDriver;

@ShellComponent
public class ShellCommands {
    private ValidateDriver validateDriver;

    public ShellCommands(ValidateDriver validateDriver) {
        this.validateDriver = validateDriver;
    }

    @ShellMethod("Create an instance of an object")
    public String create(@ShellOption String kind) {
        if (!validateDriver.validateCreateParam(kind))
            return String.format("'%s' is not a known entity", kind);

        return kind;
    }
}
