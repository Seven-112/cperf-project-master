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
 * Criteria class for the {@link com.mshz.microproject.domain.ProjectTaskStatusTrakingFile} entity. This class is used
 * in {@link com.mshz.microproject.web.rest.ProjectTaskStatusTrakingFileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /project-task-status-traking-files?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjectTaskStatusTrakingFileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter fileId;

    private StringFilter fileName;

    private LongFilter trackId;

    public ProjectTaskStatusTrakingFileCriteria() {
    }

    public ProjectTaskStatusTrakingFileCriteria(ProjectTaskStatusTrakingFileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fileId = other.fileId == null ? null : other.fileId.copy();
        this.fileName = other.fileName == null ? null : other.fileName.copy();
        this.trackId = other.trackId == null ? null : other.trackId.copy();
    }

    @Override
    public ProjectTaskStatusTrakingFileCriteria copy() {
        return new ProjectTaskStatusTrakingFileCriteria(this);
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

    public LongFilter getTrackId() {
        return trackId;
    }

    public void setTrackId(LongFilter trackId) {
        this.trackId = trackId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProjectTaskStatusTrakingFileCriteria that = (ProjectTaskStatusTrakingFileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fileId, that.fileId) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(trackId, that.trackId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fileId,
        fileName,
        trackId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectTaskStatusTrakingFileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fileId != null ? "fileId=" + fileId + ", " : "") +
                (fileName != null ? "fileName=" + fileName + ", " : "") +
                (trackId != null ? "trackId=" + trackId + ", " : "") +
            "}";
    }

}
