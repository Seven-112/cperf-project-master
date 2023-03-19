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

/**
 * Criteria class for the {@link com.mshz.microproject.domain.ProjectComment} entity. This class is used
 * in {@link com.mshz.microproject.web.rest.ProjectCommentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /project-comments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjectCommentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter projectId;

    private LongFilter userId;

    private StringFilter userName;

    private StringFilter userEmail;

    public ProjectCommentCriteria() {
    }

    public ProjectCommentCriteria(ProjectCommentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        this.userEmail = other.userEmail == null ? null : other.userEmail.copy();
    }

    @Override
    public ProjectCommentCriteria copy() {
        return new ProjectCommentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getProjectId() {
        return projectId;
    }

    public void setProjectId(LongFilter projectId) {
        this.projectId = projectId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProjectCommentCriteria that = (ProjectCommentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(projectId, that.projectId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(userEmail, that.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        projectId,
        userId,
        userName,
        userEmail
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectCommentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (projectId != null ? "projectId=" + projectId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (userName != null ? "userName=" + userName + ", " : "") +
                (userEmail != null ? "userEmail=" + userEmail + ", " : "") +
            "}";
    }

}
