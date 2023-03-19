package com.mshz.microproject.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.mshz.microproject.domain.enumeration.ProjectEventRecurrence;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.mshz.microproject.domain.ProjectEventTrigger} entity. This class is used
 * in {@link com.mshz.microproject.web.rest.ProjectEventTriggerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /project-event-triggers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjectEventTriggerCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ProjectEventRecurrence
     */
    public static class ProjectEventRecurrenceFilter extends Filter<ProjectEventRecurrence> {

        public ProjectEventRecurrenceFilter() {
        }

        public ProjectEventRecurrenceFilter(ProjectEventRecurrenceFilter filter) {
            super(filter);
        }

        @Override
        public ProjectEventRecurrenceFilter copy() {
            return new ProjectEventRecurrenceFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter editorId;

    private InstantFilter createdAt;

    private StringFilter name;

    private ProjectEventRecurrenceFilter recurrence;

    private BooleanFilter disabled;

    private StringFilter editorName;

    private IntegerFilter hour;

    private IntegerFilter minute;

    private InstantFilter firstStartedAt;

    private LocalDateFilter sheduledOn;

    private LongFilter processId;

    public ProjectEventTriggerCriteria() {
    }

    public ProjectEventTriggerCriteria(ProjectEventTriggerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.editorId = other.editorId == null ? null : other.editorId.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.recurrence = other.recurrence == null ? null : other.recurrence.copy();
        this.disabled = other.disabled == null ? null : other.disabled.copy();
        this.editorName = other.editorName == null ? null : other.editorName.copy();
        this.hour = other.hour == null ? null : other.hour.copy();
        this.minute = other.minute == null ? null : other.minute.copy();
        this.firstStartedAt = other.firstStartedAt == null ? null : other.firstStartedAt.copy();
        this.sheduledOn = other.sheduledOn == null ? null : other.sheduledOn.copy();
        this.processId = other.processId == null ? null : other.processId.copy();
    }

    @Override
    public ProjectEventTriggerCriteria copy() {
        return new ProjectEventTriggerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getEditorId() {
        return editorId;
    }

    public void setEditorId(LongFilter editorId) {
        this.editorId = editorId;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public ProjectEventRecurrenceFilter getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(ProjectEventRecurrenceFilter recurrence) {
        this.recurrence = recurrence;
    }

    public BooleanFilter getDisabled() {
        return disabled;
    }

    public void setDisabled(BooleanFilter disabled) {
        this.disabled = disabled;
    }

    public StringFilter getEditorName() {
        return editorName;
    }

    public void setEditorName(StringFilter editorName) {
        this.editorName = editorName;
    }

    public IntegerFilter getHour() {
        return hour;
    }

    public void setHour(IntegerFilter hour) {
        this.hour = hour;
    }

    public IntegerFilter getMinute() {
        return minute;
    }

    public void setMinute(IntegerFilter minute) {
        this.minute = minute;
    }

    public InstantFilter getFirstStartedAt() {
        return firstStartedAt;
    }

    public void setFirstStartedAt(InstantFilter firstStartedAt) {
        this.firstStartedAt = firstStartedAt;
    }

    public LocalDateFilter getSheduledOn() {
        return sheduledOn;
    }

    public void setSheduledOn(LocalDateFilter sheduledOn) {
        this.sheduledOn = sheduledOn;
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
        final ProjectEventTriggerCriteria that = (ProjectEventTriggerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(editorId, that.editorId) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(name, that.name) &&
            Objects.equals(recurrence, that.recurrence) &&
            Objects.equals(disabled, that.disabled) &&
            Objects.equals(editorName, that.editorName) &&
            Objects.equals(hour, that.hour) &&
            Objects.equals(minute, that.minute) &&
            Objects.equals(firstStartedAt, that.firstStartedAt) &&
            Objects.equals(sheduledOn, that.sheduledOn) &&
            Objects.equals(processId, that.processId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        editorId,
        createdAt,
        name,
        recurrence,
        disabled,
        editorName,
        hour,
        minute,
        firstStartedAt,
        sheduledOn,
        processId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectEventTriggerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (editorId != null ? "editorId=" + editorId + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (recurrence != null ? "recurrence=" + recurrence + ", " : "") +
                (disabled != null ? "disabled=" + disabled + ", " : "") +
                (editorName != null ? "editorName=" + editorName + ", " : "") +
                (hour != null ? "hour=" + hour + ", " : "") +
                (minute != null ? "minute=" + minute + ", " : "") +
                (firstStartedAt != null ? "firstStartedAt=" + firstStartedAt + ", " : "") +
                (sheduledOn != null ? "sheduledOn=" + sheduledOn + ", " : "") +
                (processId != null ? "processId=" + processId + ", " : "") +
            "}";
    }

}
