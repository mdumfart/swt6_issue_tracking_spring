package swt6.spring.worklog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.logic.EmployeeLogic;
import swt6.spring.worklog.logic.EmployeeLogicImpl;
import swt6.spring.worklog.logic.ProjectLogic;
import swt6.spring.worklog.logic.ProjectLogicImpl;

import static swt6.util.PrintUtil.printSeparator;
import static swt6.util.PrintUtil.printTitle;

import java.time.LocalDate;

public class Client {
    private static void test() {
        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
                "swt6/spring/worklog/applicationContext.xml")) {

            EmployeeLogic employeeLogic = factory.getBean(EmployeeLogic.class);

            Employee employee1 = new Employee("Michael", "Dumfart", LocalDate.of(1997, 5, 14));

            employee1 = employeeLogic.save(employee1);

            System.out.println(employee1);
        }
    }

    public static void main(String[] args) {
        test();
    }
}
