package com.mshz.microproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.mshz.microproject.domain.enumeration.ProjectStartableTaskCond;

/**
 * A ProjectStartableTask.
 */
@Entity
@Table(name = "prj_startable_tasks")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectStartableTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "trigger_task_id")
    private Long triggerTaskId;

    @Column(name = "startable_task_id")
    private Long startableTaskId;

    @Column(name = "trigger_task_name")
    private String triggerTaskName;

    @Column(name = "startable_task_name")
    private String startableTaskName;

    @Column(name = "trigger_project_name")
    private String triggerProjectName;

    @Column(name = "startable_project_name")
    private String startableProjectName;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "created_at")
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "start_cond")
    private ProjectStartableTaskCond startCond;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTriggerTaskId() {
        return triggerTaskId;
    }

    public ProjectStartableTask triggerTaskId(Long triggerTaskId) {
        this.triggerTaskId = triggerTaskId;
        return this;
    }

    public void setTriggerTaskId(Long triggerTaskId) {
        this.triggerTaskId = triggerTaskId;
    }

    public Long getStartableTaskId() {
        return startableTaskId;
    }

    public ProjectStartableTask startableTaskId(Long startableTaskId) {
        this.startableTaskId = startableTaskId;
        return this;
    }

    public void setStartableTaskId(Long startableTaskId) {
        this.startableTaskId = startableTaskId;
    }

    public String getTriggerTaskName() {
        return triggerTaskName;
    }

    public ProjectStartableTask triggerTaskName(String triggerTaskName) {
        this.triggerTaskName = triggerTaskName;
        return this;
    }

    public void setTriggerTaskName(String triggerTaskName) {
        this.triggerTaskName = triggerTaskName;
    }

    public String getStartableTaskName() {
        return startableTaskName;
    }

    public ProjectStartableTask startableTaskName(String startableTaskName) {
        this.startableTaskName = startableTaskName;
        return this;
    }

    public void setStartableTaskName(String startableTaskName) {
        this.startableTaskName = startableTaskName;
    }

    public String getTriggerProjectName() {
        return triggerProjectName;
    }

    public ProjectStartableTask triggerProjectName(String triggerProjectName) {
        this.triggerProjectName = triggerProjectName;
        return this;
    }

    public void setTriggerProjectName(String triggerProjectName) {
        this.triggerProjectName = triggerProjectName;
    }

    public String getStartableProjectName() {
        return startableProjectName;
    }

    public ProjectStartableTask startableProjectName(String startableProjectName) {
        this.startableProjectName = startableProjectName;
        return this;
    }

    public void setStartableProjectName(String startableProjectName) {
        this.startableProjectName = startableProjectName;
    }

    public Long getUserId() {
        return userId;
    }

    public ProjectStartableTask userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public ProjectStartableTask createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public ProjectStartableTaskCond getStartCond() {
        return startCond;
    }

    public ProjectStartableTask startCond(ProjectStartableTaskCond startCond) {
        this.startCond = startCond;
        return this;
    }

    public void setStartCond(ProjectStartableTaskCond startCond) {
        this.startCond = startCond;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectStartableTask)) {
            return false;
        }
        return id != null && id.equals(((ProjectStartableTask) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectStartableTask{" +
            "id=" + getId() +
            ", triggerTaskId=" + getTriggerTaskId() +
            ", startableTaskId=" + getStartableTaskId() +
            ", triggerTaskName='" + getTriggerTaskName() + "'" +
            ", startableTaskName='" + getStartableTaskName() + "'" +
            ", triggerProjectName='" + getTriggerProjectName() + "'" +
            ", startableProjectName='" + getStartableProjectName() + "'" +
            ", userId=" + getUserId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", startCond='" + getStartCond() + "'" +
            "}";
    }
}
