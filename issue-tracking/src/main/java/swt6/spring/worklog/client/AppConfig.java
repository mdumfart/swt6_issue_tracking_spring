package swt6.spring.worklog.client;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan
@ImportResource("swt6/spring/worklog/applicationContext.xml")
public class AppConfig {
}
