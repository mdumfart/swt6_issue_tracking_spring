package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.spring.worklog.domain.Issue;

@Repository
public interface IssueDao extends JpaRepository<Issue, Long> {
}
