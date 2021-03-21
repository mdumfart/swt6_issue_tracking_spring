package swt6.spring.worklog.services;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.ProjectDao;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Issue;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.domain.Project;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
    private ProjectDao projectDao;
    private EmployeeService employeeService;
    private LogbookEntryService logbookEntryService;

    public ProjectServiceImpl(ProjectDao projectDao, EmployeeService employeeService, LogbookEntryService logbookEntryService) {
        this.projectDao = projectDao;
        this.employeeService = employeeService;
        this.logbookEntryService = logbookEntryService;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Project> findById(long id) {
        return projectDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findAll() {
        return projectDao.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Project save(Project project) {
        return projectDao.save(project);
    }

    @Override
    public Project assignEmployeeToProject(Employee employee, Project project) {
        project.addEmployee(employee);

        return projectDao.save(project);
    }

    @Override
    public Project removeEmployeeFromProject(Employee employee, Project project) {
        project.removeEmployee(employee);

        return projectDao.save(project);
    }

    @Override
    public double getTimeInvestedInProjectByEmployee(Project project, Employee employee) throws IllegalArgumentException {
        Optional<Project> optionalP = projectDao.findById(project.getId());
        Optional<Employee> optionalE = employeeService.findById(employee.getId());

        checkProjectAndEmployeeAvailable(optionalP, optionalE);

        Project p = optionalP.get();
        Employee e = optionalE.get();

        if (!employeeService.findByProject(p).contains(e)) throw new IllegalArgumentException("Employee is not assigned to project");

        double timeSum = 0;

        for (LogbookEntry lbe : logbookEntryService.findByProjectAndEmployee(p, e)) {
            timeSum += lbe.getTimeSpent();
        }

        return timeSum;
    }

    @Override
    public double getTimeToInvestInProjectByEmployee(Project project, Employee employee) {
        Optional<Project> optionalP = projectDao.findById(project.getId());
        Optional<Employee> optionalE = employeeService.findById(employee.getId());

        checkProjectAndEmployeeAvailable(optionalP, optionalE);

        Project p = optionalP.get();
        Employee e = optionalE.get();

        if (!employeeService.findByProject(p).contains(e)) throw new IllegalArgumentException("Employee is not assigned to project");

        double timeSumEstimated = 0;
        double timeSumSpent = 0;

        List<Issue> issues = p.getIssues().stream().filter(issue -> issue.getEmployee().equals(e)).collect(Collectors.toList());
        for (Issue i : issues) {
            timeSumEstimated += i.getEstimatedTime();
            timeSumEstimated += i.getExpendedTime();
        }

        return timeSumEstimated - timeSumSpent;
    }

    private void checkProjectAndEmployeeAvailable(Optional<Project> project, Optional<Employee> employee) {
        if (!project.isPresent()) throw new IllegalArgumentException("Project not found");
        if (!employee.isPresent()) throw new IllegalArgumentException("Employee not found");
    }
}
