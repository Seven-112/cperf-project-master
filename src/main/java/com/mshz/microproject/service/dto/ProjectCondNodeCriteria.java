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
 * Criteria class for the {@link com.mshz.microproject.domain.ProjectCondNode} entity. This class is used
 * in {@link com.mshz.microproject.web.rest.ProjectCondNodeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /project-cond-nodes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjectCondNodeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter logigramPosX;

    private DoubleFilter logigramPosY;

    private LongFilter projectId;

    public ProjectCondNodeCriteria() {
    }

    public ProjectCondNodeCriteria(ProjectCondNodeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.logigramPosX = other.logigramPosX == null ? null : other.logigramPosX.copy();
        this.logigramPosY = other.logigramPosY == null ? null : other.logigramPosY.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
    }

    @Override
    public ProjectCondNodeCriteria copy() {
        return new ProjectCondNodeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getLogigramPosX() {
        return logigramPosX;
    }

    public void setLogigramPosX(DoubleFilter logigramPosX) {
        this.logigramPosX = logigramPosX;
    }

    public DoubleFilter getLogigramPosY() {
        return logigramPosY;
    }

    public void setLogigramPosY(DoubleFilter logigramPosY) {
        this.logigramPosY = logigramPosY;
    }

    public LongFilter getProjectId() {
        return projectId;
    }

    public void setProjectId(LongFilter projectId) {
        this.projectId = projectId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProjectCondNodeCriteria that = (ProjectCondNodeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(logigramPosX, that.logigramPosX) &&
            Objects.equals(logigramPosY, that.logigramPosY) &&
            Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        logigramPosX,
        logigramPosY,
        projectId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectCondNodeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (logigramPosX != null ? "logigramPosX=" + logigramPosX + ", " : "") +
                (logigramPosY != null ? "logigramPosY=" + logigramPosY + ", " : "") +
                (projectId != null ? "projectId=" + projectId + ", " : "") +
            "}";
    }

}
