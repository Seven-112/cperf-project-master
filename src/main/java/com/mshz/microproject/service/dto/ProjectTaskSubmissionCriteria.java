package com.mshz.microproject.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.mshz.microproject.domain.ProjectTaskSubmission} entity. This class is used
 * in {@link com.mshz.microproject.web.rest.ProjectTaskSubmissionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /project-task-submissions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjectTaskSubmissionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter submitorId;

    private StringFilter submitorName;

    private StringFilter submitorEmail;

    private InstantFilter storeUp;

    private LongFilter taskId;

    public ProjectTaskSubmissionCriteria() {
    }

    public ProjectTaskSubmissionCriteria(ProjectTaskSubmissionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.submitorId = other.submitorId == null ? null : other.submitorId.copy();
        this.submitorName = other.submitorName == null ? null : other.submitorName.copy();
        this.submitorEmail = other.submitorEmail == null ? null : other.submitorEmail.copy();
        this.storeUp = other.storeUp == null ? null : other.storeUp.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
    }

    @Override
    public ProjectTaskSubmissionCriteria copy() {
        return new ProjectTaskSubmissionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getSubmitorId() {
        return submitorId;
    }

    public void setSubmitorId(LongFilter submitorId) {
        this.submitorId = submitorId;
    }

    public StringFilter getSubmitorName() {
        return submitorName;
    }

    public void setSubmitorName(StringFilter submitorName) {
        this.submitorName = submitorName;
    }

    public StringFilter getSubmitorEmail() {
        return submitorEmail;
    }

    public void setSubmitorEmail(StringFilter submitorEmail) {
        this.submitorEmail = submitorEmail;
    }

    public InstantFilter getStoreUp() {
        return storeUp;
    }

    public void setStoreUp(InstantFilter storeUp) {
        this.storeUp = storeUp;
    }

    public LongFilter getTaskId() {
        return taskId;
    }

    public void setTaskId(LongFilter taskId) {
        this.taskId = taskId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProjectTaskSubmissionCriteria that = (ProjectTaskSubmissionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(submitorId, that.submitorId) &&
            Objects.equals(submitorName, that.submitorName) &&
            Objects.equals(submitorEmail, that.submitorEmail) &&
            Objects.equals(storeUp, that.storeUp) &&
            Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        submitorId,
        submitorName,
        submitorEmail,
        storeUp,
        taskId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectTaskSubmissionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (submitorId != null ? "submitorId=" + submitorId + ", " : "") +
                (submitorName != null ? "submitorName=" + submitorName + ", " : "") +
                (submitorEmail != null ? "submitorEmail=" + submitorEmail + ", " : "") +
                (storeUp != null ? "storeUp=" + storeUp + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
            "}";
    }

}
