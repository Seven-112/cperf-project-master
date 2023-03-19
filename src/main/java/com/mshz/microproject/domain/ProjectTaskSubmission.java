package com.mshz.microproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ProjectTaskSubmission.
 */
@Entity
@Table(name = "prj_task_submission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectTaskSubmission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "submitor_id")
    private Long submitorId;

    @NotNull
    @Column(name = "submitor_name", nullable = false)
    private String submitorName;

    @NotNull
    @Column(name = "submitor_email", nullable = false)
    private String submitorEmail;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "comment")
    private String comment;

    @Column(name = "store_up")
    private Instant storeUp;

    @NotNull
    @Column(name = "task_id", nullable = false)
    private Long taskId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubmitorId() {
        return submitorId;
    }

    public ProjectTaskSubmission submitorId(Long submitorId) {
        this.submitorId = submitorId;
        return this;
    }

    public void setSubmitorId(Long submitorId) {
        this.submitorId = submitorId;
    }

    public String getSubmitorName() {
        return submitorName;
    }

    public ProjectTaskSubmission submitorName(String submitorName) {
        this.submitorName = submitorName;
        return this;
    }

    public void setSubmitorName(String submitorName) {
        this.submitorName = submitorName;
    }

    public String getSubmitorEmail() {
        return submitorEmail;
    }

    public ProjectTaskSubmission submitorEmail(String submitorEmail) {
        this.submitorEmail = submitorEmail;
        return this;
    }

    public void setSubmitorEmail(String submitorEmail) {
        this.submitorEmail = submitorEmail;
    }

    public String getComment() {
        return comment;
    }

    public ProjectTaskSubmission comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getStoreUp() {
        return storeUp;
    }

    public ProjectTaskSubmission storeUp(Instant storeUp) {
        this.storeUp = storeUp;
        return this;
    }

    public void setStoreUp(Instant storeUp) {
        this.storeUp = storeUp;
    }

    public Long getTaskId() {
        return taskId;
    }

    public ProjectTaskSubmission taskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectTaskSubmission)) {
            return false;
        }
        return id != null && id.equals(((ProjectTaskSubmission) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectTaskSubmission{" +
            "id=" + getId() +
            ", submitorId=" + getSubmitorId() +
            ", submitorName='" + getSubmitorName() + "'" +
            ", submitorEmail='" + getSubmitorEmail() + "'" +
            ", comment='" + getComment() + "'" +
            ", storeUp='" + getStoreUp() + "'" +
            ", taskId=" + getTaskId() +
            "}";
    }
}
