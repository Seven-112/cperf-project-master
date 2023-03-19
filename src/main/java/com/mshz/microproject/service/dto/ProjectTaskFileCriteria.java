package com.mshz.microproject.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.mshz.microproject.domain.enumeration.ProjectTaskFileType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mshz.microproject.domain.ProjectTaskFile} entity. This class is used
 * in {@link com.mshz.microproject.web.rest.ProjectTaskFileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /project-task-files?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjectTaskFileCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ProjectTaskFileType
     */
    public static class ProjectTaskFileTypeFilter extends Filter<ProjectTaskFileType> {

        public ProjectTaskFileTypeFilter() {
        }

        public ProjectTaskFileTypeFilter(ProjectTaskFileTypeFilter filter) {
            super(filter);
        }

        @Override
        public ProjectTaskFileTypeFilter copy() {
            return new ProjectTaskFileTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter fileId;

    private StringFilter fileName;

    private ProjectTaskFileTypeFilter type;

    private LongFilter taskId;

    public ProjectTaskFileCriteria() {
    }

    public ProjectTaskFileCriteria(ProjectTaskFileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fileId = other.fileId == null ? null : other.fileId.copy();
        this.fileName = other.fileName == null ? null : other.fileName.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
    }

    @Override
    public ProjectTaskFileCriteria copy() {
        return new ProjectTaskFileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getFileId() {
        return fileId;
    }

    public void setFileId(LongFilter fileId) {
        this.fileId = fileId;
    }

    public StringFilter getFileName() {
        return fileName;
    }

    public void setFileName(StringFilter fileName) {
        this.fileName = fileName;
    }

    public ProjectTaskFileTypeFilter getType() {
        return type;
    }

    public void setType(ProjectTaskFileTypeFilter type) {
        this.type = type;
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
        final ProjectTaskFileCriteria that = (ProjectTaskFileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fileId, that.fileId) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(type, that.type) &&
            Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fileId,
        fileName,
        type,
        taskId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectTaskFileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fileId != null ? "fileId=" + fileId + ", " : "") +
                (fileName != null ? "fileName=" + fileName + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
            "}";
    }

}
