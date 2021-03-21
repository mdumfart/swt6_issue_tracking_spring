package swt6.spring.worklog.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.shell.InputProvider;
import org.springframework.shell.Shell;
import swt6.spring.worklog.client.shell.ShellConfig;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.services.EmployeeService;

import java.io.IOException;
import java.time.LocalDate;

public class App {
    private static EmployeeService employeeService;

    public static void main(String[] args) throws IOException {
        try (AbstractApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class, ShellConfig.class)) {

            employeeService = context.getBean(EmployeeService.class);
            setupApplication();

            Shell shell = context.getBean(Shell.class);
            shell.run(context.getBean(InputProvider.class));
        }
    }

    private static void setupApplication() {
        Employee empl1 = new Employee("Michael", "Dumfart", LocalDate.of(1997, 5, 14));
        Employee empl2 = new Employee("Peter", "Test", LocalDate.of(1960, 8, 22));
        Employee empl3 = new Employee("Hans", "Zimmer", LocalDate.of(1975, 1, 1));

        employeeService.save(empl1);
        employeeService.save(empl2);
        employeeService.save(empl3);
    }
}
