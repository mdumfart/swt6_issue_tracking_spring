package swt6.spring.worklog.logic;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.ProjectDao;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Project;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ProjectLogicImpl implements ProjectLogic {
    private ProjectDao projectDao;

    public ProjectLogicImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Project> findById(long id) {
        return projectDao.findById(id);
    }

    @Override
    public Set<Project> findAll() {
        return null;
    }

    @Override
    public Project save(Project project) {
        return null;
    }

    @Override
    public Project assignEmployeeToProject(Employee employee, Project project) {
        return null;
    }

    @Override
    public Project removeEmployeeFromProject(Employee employee, Project project) {
        return null;
    }
}
