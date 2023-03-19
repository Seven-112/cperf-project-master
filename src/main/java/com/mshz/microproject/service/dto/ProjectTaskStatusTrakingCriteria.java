package com.mshz.microproject.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.mshz.microproject.domain.enumeration.ProjectTaskStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.mshz.microproject.domain.ProjectTaskStatusTraking} entity. This class is used
 * in {@link com.mshz.microproject.web.rest.ProjectTaskStatusTrakingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /project-task-status-trakings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjectTaskStatusTrakingCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ProjectTaskStatus
     */
    public static class ProjectTaskStatusFilter extends Filter<ProjectTaskStatus> {

        public ProjectTaskStatusFilter() {
        }

        public ProjectTaskStatusFilter(ProjectTaskStatusFilter filter) {
            super(filter);
        }

        @Override
        public ProjectTaskStatusFilter copy() {
            return new ProjectTaskStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter taskId;

    private ProjectTaskStatusFilter status;

    private InstantFilter tracingAt;

    private LongFilter userId;

    private StringFilter userName;

    private StringFilter userEmail;

    private BooleanFilter editable;

    public ProjectTaskStatusTrakingCriteria() {
    }

    public ProjectTaskStatusTrakingCriteria(ProjectTaskStatusTrakingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.tracingAt = other.tracingAt == null ? null : other.tracingAt.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        this.userEmail = other.userEmail == null ? null : other.userEmail.copy();
        this.editable = other.editable == null ? null : other.editable.copy();
    }

    @Override
    public ProjectTaskStatusTrakingCriteria copy() {
        return new ProjectTaskStatusTrakingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getTaskId() {
        return taskId;
    }

    public void setTaskId(LongFilter taskId) {
        this.taskId = taskId;
    }

    public ProjectTaskStatusFilter getStatus() {
        return status;
    }

    public void setStatus(ProjectTaskStatusFilter status) {
        this.status = status;
    }

    public InstantFilter getTracingAt() {
        return tracingAt;
    }

    public void setTracingAt(InstantFilter tracingAt) {
        this.tracingAt = tracingAt;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public StringFilter getUserName() {
        return userName;
    }

    public void setUserName(StringFilter userName) {
        this.userName = userName;
    }

    public StringFilter getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(StringFilter userEmail) {
        this.userEmail = userEmail;
    }

    public BooleanFilter getEditable() {
        return editable;
    }

    public void setEditable(BooleanFilter editable) {
        this.editable = editable;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProjectTaskStatusTrakingCriteria that = (ProjectTaskStatusTrakingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(taskId, that.taskId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(tracingAt, that.tracingAt) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(userEmail, that.userEmail) &&
            Objects.equals(editable, that.editable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        taskId,
        status,
        tracingAt,
        userId,
        userName,
        userEmail,
        editable
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectTaskStatusTrakingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (tracingAt != null ? "tracingAt=" + tracingAt + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (userName != null ? "userName=" + userName + ", " : "") +
                (userEmail != null ? "userEmail=" + userEmail + ", " : "") +
                (editable != null ? "editable=" + editable + ", " : "") +
            "}";
    }

}
