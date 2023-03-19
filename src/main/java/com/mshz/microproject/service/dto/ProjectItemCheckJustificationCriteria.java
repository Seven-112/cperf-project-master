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
 * Criteria class for the {@link com.mshz.microproject.domain.ProjectItemCheckJustification} entity. This class is used
 * in {@link com.mshz.microproject.web.rest.ProjectItemCheckJustificationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /project-item-check-justifications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjectItemCheckJustificationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter checked;

    private LongFilter taskItemId;

    private InstantFilter dateAndTime;

    public ProjectItemCheckJustificationCriteria() {
    }

    public ProjectItemCheckJustificationCriteria(ProjectItemCheckJustificationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.checked = other.checked == null ? null : other.checked.copy();
        this.taskItemId = other.taskItemId == null ? null : other.taskItemId.copy();
        this.dateAndTime = other.dateAndTime == null ? null : other.dateAndTime.copy();
    }

    @Override
    public ProjectItemCheckJustificationCriteria copy() {
        return new ProjectItemCheckJustificationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getChecked() {
        return checked;
    }

    public void setChecked(BooleanFilter checked) {
        this.checked = checked;
    }

    public LongFilter getTaskItemId() {
        return taskItemId;
    }

    public void setTaskItemId(LongFilter taskItemId) {
        this.taskItemId = taskItemId;
    }

    public InstantFilter getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(InstantFilter dateAndTime) {
        this.dateAndTime = dateAndTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProjectItemCheckJustificationCriteria that = (ProjectItemCheckJustificationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(checked, that.checked) &&
            Objects.equals(taskItemId, that.taskItemId) &&
            Objects.equals(dateAndTime, that.dateAndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        checked,
        taskItemId,
        dateAndTime
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectItemCheckJustificationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (checked != null ? "checked=" + checked + ", " : "") +
                (taskItemId != null ? "taskItemId=" + taskItemId + ", " : "") +
                (dateAndTime != null ? "dateAndTime=" + dateAndTime + ", " : "") +
            "}";
    }

}
