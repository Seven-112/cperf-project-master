package com.mshz.microproject.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.mshz.microproject.domain.enumeration.ProjectPriority;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.mshz.microproject.domain.Project} entity. This class is used
 * in {@link com.mshz.microproject.web.rest.ProjectResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /projects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjectCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ProjectPriority
     */
    public static class ProjectPriorityFilter extends Filter<ProjectPriority> {

        public ProjectPriorityFilter() {
        }

        public ProjectPriorityFilter(ProjectPriorityFilter filter) {
            super(filter);
        }

        @Override
        public ProjectPriorityFilter copy() {
            return new ProjectPriorityFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter label;

    private ProjectPriorityFilter priorityLevel;

    private BooleanFilter valid;

    private InstantFilter previewStartAt;

    private InstantFilter startAt;

    private InstantFilter previewFinishAt;

    private InstantFilter finishedAt;

    private InstantFilter createdAt;

    private IntegerFilter startCount;

    private LongFilter parentId;

    private LongFilter editorId;

    private LongFilter runnableProcessId;

    private LongFilter categoryId;

    private LongFilter responsableId;

    private StringFilter responsableName;

    private StringFilter responsableEmail;

    private IntegerFilter ponderation;

    private IntegerFilter taskGlobalPonderation;

    private StringFilter path;

    public ProjectCriteria() {
    }

    public ProjectCriteria(ProjectCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.label = other.label == null ? null : other.label.copy();
        this.priorityLevel = other.priorityLevel == null ? null : other.priorityLevel.copy();
        this.valid = other.valid == null ? null : other.valid.copy();
        this.previewStartAt = other.previewStartAt == null ? null : other.previewStartAt.copy();
        this.startAt = other.startAt == null ? null : other.startAt.copy();
        this.previewFinishAt = other.previewFinishAt == null ? null : other.previewFinishAt.copy();
        this.finishedAt = other.finishedAt == null ? null : other.finishedAt.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.startCount = other.startCount == null ? null : other.startCount.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
        this.editorId = other.editorId == null ? null : other.editorId.copy();
        this.runnableProcessId = other.runnableProcessId == null ? null : other.runnableProcessId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.responsableId = other.responsableId == null ? null : other.responsableId.copy();
        this.responsableName = other.responsableName == null ? null : other.responsableName.copy();
        this.responsableEmail = other.responsableEmail == null ? null : other.responsableEmail.copy();
        this.ponderation = other.ponderation == null ? null : other.ponderation.copy();
        this.taskGlobalPonderation = other.taskGlobalPonderation == null ? null : other.taskGlobalPonderation.copy();
        this.path = other.path == null ? null : other.path.copy();
    }

    @Override
    public ProjectCriteria copy() {
        return new ProjectCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLabel() {
        return label;
    }

    public void setLabel(StringFilter label) {
        this.label = label;
    }

    public ProjectPriorityFilter getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(ProjectPriorityFilter priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public BooleanFilter getValid() {
        return valid;
    }

    public void setValid(BooleanFilter valid) {
        this.valid = valid;
    }

    public InstantFilter getPreviewStartAt() {
        return previewStartAt;
    }

    public void setPreviewStartAt(InstantFilter previewStartAt) {
        this.previewStartAt = previewStartAt;
    }

    public InstantFilter getStartAt() {
        return startAt;
    }

    public void setStartAt(InstantFilter startAt) {
        this.startAt = startAt;
    }

    public InstantFilter getPreviewFinishAt() {
        return previewFinishAt;
    }

    public void setPreviewFinishAt(InstantFilter previewFinishAt) {
        this.previewFinishAt = previewFinishAt;
    }

    public InstantFilter getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(InstantFilter finishedAt) {
        this.finishedAt = finishedAt;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public IntegerFilter getStartCount() {
        return startCount;
    }

    public void setStartCount(IntegerFilter startCount) {
        this.startCount = startCount;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }

    public LongFilter getEditorId() {
        return editorId;
    }

    public void setEditorId(LongFilter editorId) {
        this.editorId = editorId;
    }

    public LongFilter getRunnableProcessId() {
        return runnableProcessId;
    }

    public void setRunnableProcessId(LongFilter runnableProcessId) {
        this.runnableProcessId = runnableProcessId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(LongFilter responsableId) {
        this.responsableId = responsableId;
    }

    public StringFilter getResponsableName() {
        return responsableName;
    }

    public void setResponsableName(StringFilter responsableName) {
        this.responsableName = responsableName;
    }

    public StringFilter getResponsableEmail() {
        return responsableEmail;
    }

    public void setResponsableEmail(StringFilter responsableEmail) {
        this.responsableEmail = responsableEmail;
    }

    public IntegerFilter getPonderation() {
        return ponderation;
    }

    public void setPonderation(IntegerFilter ponderation) {
        this.ponderation = ponderation;
    }

    public IntegerFilter getTaskGlobalPonderation() {
        return taskGlobalPonderation;
    }

    public void setTaskGlobalPonderation(IntegerFilter taskGlobalPonderation) {
        this.taskGlobalPonderation = taskGlobalPonderation;
    }

    public StringFilter getPath() {
        return path;
    }

    public void setPath(StringFilter path) {
        this.path = path;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProjectCriteria that = (ProjectCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(label, that.label) &&
            Objects.equals(priorityLevel, that.priorityLevel) &&
            Objects.equals(valid, that.valid) &&
            Objects.equals(previewStartAt, that.previewStartAt) &&
            Objects.equals(startAt, that.startAt) &&
            Objects.equals(previewFinishAt, that.previewFinishAt) &&
            Objects.equals(finishedAt, that.finishedAt) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(startCount, that.startCount) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(editorId, that.editorId) &&
            Objects.equals(runnableProcessId, that.runnableProcessId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(responsableId, that.responsableId) &&
            Objects.equals(responsableName, that.responsableName) &&
            Objects.equals(responsableEmail, that.responsableEmail) &&
            Objects.equals(ponderation, that.ponderation) &&
            Objects.equals(taskGlobalPonderation, that.taskGlobalPonderation) &&
            Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        label,
        priorityLevel,
        valid,
        previewStartAt,
        startAt,
        previewFinishAt,
        finishedAt,
        createdAt,
        startCount,
        parentId,
        editorId,
        runnableProcessId,
        categoryId,
        responsableId,
        responsableName,
        responsableEmail,
        ponderation,
        taskGlobalPonderation,
        path
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (label != null ? "label=" + label + ", " : "") +
                (priorityLevel != null ? "priorityLevel=" + priorityLevel + ", " : "") +
                (valid != null ? "valid=" + valid + ", " : "") +
                (previewStartAt != null ? "previewStartAt=" + previewStartAt + ", " : "") +
                (startAt != null ? "startAt=" + startAt + ", " : "") +
                (previewFinishAt != null ? "previewFinishAt=" + previewFinishAt + ", " : "") +
                (finishedAt != null ? "finishedAt=" + finishedAt + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (startCount != null ? "startCount=" + startCount + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
                (editorId != null ? "editorId=" + editorId + ", " : "") +
                (runnableProcessId != null ? "runnableProcessId=" + runnableProcessId + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
                (responsableId != null ? "responsableId=" + responsableId + ", " : "") +
                (responsableName != null ? "responsableName=" + responsableName + ", " : "") +
                (responsableEmail != null ? "responsableEmail=" + responsableEmail + ", " : "") +
                (ponderation != null ? "ponderation=" + ponderation + ", " : "") +
                (taskGlobalPonderation != null ? "taskGlobalPonderation=" + taskGlobalPonderation + ", " : "") +
                (path != null ? "path=" + path + ", " : "") +
            "}";
    }

}
