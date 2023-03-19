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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.mshz.microproject.domain.ProjectPublicHoliday} entity. This class is used
 * in {@link com.mshz.microproject.web.rest.ProjectPublicHolidayResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /project-public-holidays?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjectPublicHolidayCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LocalDateFilter date;

    private LongFilter levelId;

    private LongFilter processId;

    public ProjectPublicHolidayCriteria() {
    }

    public ProjectPublicHolidayCriteria(ProjectPublicHolidayCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.levelId = other.levelId == null ? null : other.levelId.copy();
        this.processId = other.processId == null ? null : other.processId.copy();
    }

    @Override
    public ProjectPublicHolidayCriteria copy() {
        return new ProjectPublicHolidayCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public LongFilter getLevelId() {
        return levelId;
    }

    public void setLevelId(LongFilter levelId) {
        this.levelId = levelId;
    }

    public LongFilter getProcessId() {
        return processId;
    }

    public void setProcessId(LongFilter processId) {
        this.processId = processId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProjectPublicHolidayCriteria that = (ProjectPublicHolidayCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(date, that.date) &&
            Objects.equals(levelId, that.levelId) &&
            Objects.equals(processId, that.processId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        date,
        levelId,
        processId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectPublicHolidayCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (levelId != null ? "levelId=" + levelId + ", " : "") +
                (processId != null ? "processId=" + processId + ", " : "") +
            "}";
    }

}
