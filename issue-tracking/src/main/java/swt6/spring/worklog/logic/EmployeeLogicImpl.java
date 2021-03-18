package swt6.spring.worklog.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.domain.Employee;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeLogicImpl implements EmployeeLogic {
    private final EmployeeDao employeeDao;

    public EmployeeLogicImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> findById(long id) {
        return employeeDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    @Override
    public Employee save(Employee employee) {
        return employeeDao.save(employee);
    }
}
