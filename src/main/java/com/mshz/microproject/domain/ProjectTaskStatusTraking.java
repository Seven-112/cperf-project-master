package com.mshz.microproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.mshz.microproject.domain.enumeration.ProjectTaskStatus;

/**
 * A ProjectTaskStatusTraking.
 */
@Entity
@Table(name = "prj_task_status_traking")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectTaskStatusTraking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProjectTaskStatus status;

    @Column(name = "tracing_at")
    private Instant tracingAt;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "justification")
    private String justification;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "editable")
    private Boolean editable;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public ProjectTaskStatusTraking taskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public ProjectTaskStatus getStatus() {
        return status;
    }

    public ProjectTaskStatusTraking status(ProjectTaskStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProjectTaskStatus status) {
        this.status = status;
    }

    public Instant getTracingAt() {
        return tracingAt;
    }

    public ProjectTaskStatusTraking tracingAt(Instant tracingAt) {
        this.tracingAt = tracingAt;
        return this;
    }

    public void setTracingAt(Instant tracingAt) {
        this.tracingAt = tracingAt;
    }

    public String getJustification() {
        return justification;
    }

    public ProjectTaskStatusTraking justification(String justification) {
        this.justification = justification;
        return this;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public Long getUserId() {
        return userId;
    }

    public ProjectTaskStatusTraking userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public ProjectTaskStatusTraking userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public ProjectTaskStatusTraking userEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Boolean isEditable() {
        return editable;
    }

    public ProjectTaskStatusTraking editable(Boolean editable) {
        this.editable = editable;
        return this;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectTaskStatusTraking)) {
            return false;
        }
        return id != null && id.equals(((ProjectTaskStatusTraking) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectTaskStatusTraking{" +
            "id=" + getId() +
            ", taskId=" + getTaskId() +
            ", status='" + getStatus() + "'" +
            ", tracingAt='" + getTracingAt() + "'" +
            ", justification='" + getJustification() + "'" +
            ", userId=" + getUserId() +
            ", userName='" + getUserName() + "'" +
            ", userEmail='" + getUserEmail() + "'" +
            ", editable='" + isEditable() + "'" +
            "}";
    }
}
