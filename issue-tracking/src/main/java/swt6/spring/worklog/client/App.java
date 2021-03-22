package swt6.spring.worklog.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.shell.InputProvider;
import org.springframework.shell.Shell;
import swt6.spring.worklog.client.shell.ShellConfig;
import swt6.spring.worklog.client.viewmodels.UiUtil;

import java.io.IOException;

public class App {
    private static UiUtil utilDriver;

    public static void main(String[] args) throws IOException {
        try (AbstractApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class, ShellConfig.class)) {

            utilDriver = context.getBean(UiUtil.class);

            utilDriver.setupApplication();

            Shell shell = context.getBean(Shell.class);
            shell.run(context.getBean(InputProvider.class));
        }
    }

}
