package swt6.spring.worklog.domain;

import org.hibernate.annotations.FetchMode;
import swt6.spring.worklog.domain.util.IssuePriority;
import swt6.spring.worklog.domain.util.IssueState;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Issue implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueState state;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssuePriority priority;

    private double estimatedTime = 0.0d;
    private double expendedTime = 0.0d;

    @Column(nullable = false)
    private double progress = 0.0;

    @org.hibernate.annotations.Fetch(FetchMode.SELECT)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Employee employee;

    @org.hibernate.annotations.Fetch(FetchMode.SELECT)
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    // LogbookEntries are not available since they are not that important
//    @org.hibernate.annotations.Fetch(FetchMode.SELECT)
//    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
//    private Set<LogbookEntry> logbookEntries = new HashSet<>();

    public Issue() {
    }

    public Issue(String name, IssueState state, IssuePriority priority, Project project) {
        this.name = name;
        this.state = state;
        this.priority = priority;
        this.project = project;
    }

    public Issue(String name, IssueState state, IssuePriority priority, double estimatedTime, Project project) {
        this(name, state, priority, project);
        this.estimatedTime = estimatedTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IssueState getState() {
        return state;
    }

    public void setState(IssueState state) {
        this.state = state;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public double getExpendedTime() {
        return expendedTime;
    }

    public void setExpendedTime(double expendedTime) {
        this.expendedTime = expendedTime;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employees) {
        this.employee = employees;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

//    public Set<LogbookEntry> getLogbookEntries() {
//        return logbookEntries;
//    }
//
//    public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
//        this.logbookEntries = logbookEntries;
//    }
//
//    public void addLogbookEntry(LogbookEntry entry) {
//        if (entry.getIssue() != null) {
//            entry.getIssue().logbookEntries.remove(entry);
//        }
//
//        logbookEntries.add(entry);
//        entry.setIssue(this);
//    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append(String.format("Issue %d: %s, %s ", id, name, state));

        sb.append(String.format(", progress: %d%%", (int)(progress * 100)));

        if (employee != null) {
            sb.append(String.format("%n  - Assigned employee: %s", employee.toString()));
        }

        sb.append(String.format("%n  - Estimated time: %.2fh" , estimatedTime));
        sb.append(String.format("%n  - Expended time: %.2fh" , expendedTime));
        sb.append(String.format("%n  - Belongs to project %s", project.toString()));

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return Double.compare(issue.estimatedTime, estimatedTime) == 0 && Double.compare(issue.progress, progress) == 0 && id.equals(issue.id) && name.equals(issue.name) && state == issue.state && priority == issue.priority && Objects.equals(employee, issue.employee) && project.equals(issue.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, state, priority, estimatedTime, progress, employee, project);
    }
}
