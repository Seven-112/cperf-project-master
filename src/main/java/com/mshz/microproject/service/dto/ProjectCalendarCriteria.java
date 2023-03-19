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
 * Criteria class for the {@link com.mshz.microproject.domain.ProjectCalendar} entity. This class is used
 * in {@link com.mshz.microproject.web.rest.ProjectCalendarResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /project-calendars?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjectCalendarCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter dayNumber;

    private InstantFilter startTime;

    private InstantFilter endTime;

    private LongFilter projectId;

    public ProjectCalendarCriteria() {
    }

    public ProjectCalendarCriteria(ProjectCalendarCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dayNumber = other.dayNumber == null ? null : other.dayNumber.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
    }

    @Override
    public ProjectCalendarCriteria copy() {
        return new ProjectCalendarCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(IntegerFilter dayNumber) {
        this.dayNumber = dayNumber;
    }

    public InstantFilter getStartTime() {
        return startTime;
    }

    public void setStartTime(InstantFilter startTime) {
        this.startTime = startTime;
    }

    public InstantFilter getEndTime() {
        return endTime;
    }

    public void setEndTime(InstantFilter endTime) {
        this.endTime = endTime;
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
        final ProjectCalendarCriteria that = (ProjectCalendarCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dayNumber, that.dayNumber) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dayNumber,
        startTime,
        endTime,
        projectId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectCalendarCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dayNumber != null ? "dayNumber=" + dayNumber + ", " : "") +
                (startTime != null ? "startTime=" + startTime + ", " : "") +
                (endTime != null ? "endTime=" + endTime + ", " : "") +
                (projectId != null ? "projectId=" + projectId + ", " : "") +
            "}";
    }

}
