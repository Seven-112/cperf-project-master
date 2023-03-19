package com.mshz.microproject.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.mshz.microproject.domain.enumeration.ProjectTaskUserRole;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mshz.microproject.domain.ProjectTaskUser} entity. This class is used
 * in {@link com.mshz.microproject.web.rest.ProjectTaskUserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /project-task-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjectTaskUserCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ProjectTaskUserRole
     */
    public static class ProjectTaskUserRoleFilter extends Filter<ProjectTaskUserRole> {

        public ProjectTaskUserRoleFilter() {
        }

        public ProjectTaskUserRoleFilter(ProjectTaskUserRoleFilter filter) {
            super(filter);
        }

        @Override
        public ProjectTaskUserRoleFilter copy() {
            return new ProjectTaskUserRoleFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter userId;

    private StringFilter userName;

    private StringFilter userEmail;

    private ProjectTaskUserRoleFilter role;

    private LongFilter taskId;

    public ProjectTaskUserCriteria() {
    }

    public ProjectTaskUserCriteria(ProjectTaskUserCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        this.userEmail = other.userEmail == null ? null : other.userEmail.copy();
        this.role = other.role == null ? null : other.role.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
    }

    @Override
    public ProjectTaskUserCriteria copy() {
        return new ProjectTaskUserCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public ProjectTaskUserRoleFilter getRole() {
        return role;
    }

    public void setRole(ProjectTaskUserRoleFilter role) {
        this.role = role;
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
        final ProjectTaskUserCriteria that = (ProjectTaskUserCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(userEmail, that.userEmail) &&
            Objects.equals(role, that.role) &&
            Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        userId,
        userName,
        userEmail,
        role,
        taskId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectTaskUserCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (userName != null ? "userName=" + userName + ", " : "") +
                (userEmail != null ? "userEmail=" + userEmail + ", " : "") +
                (role != null ? "role=" + role + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
            "}";
    }

}
