package com.mshz.microproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.mshz.microproject.domain.enumeration.ProjectTaskFileType;

/**
 * A ProjectTaskFile.
 */
@Entity
@Table(name = "prj_task_file")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectTaskFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "file_id")
    private Long fileId;

    @Column(name = "file_name")
    private String fileName;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ProjectTaskFileType type;

    @Column(name = "task_id")
    private Long taskId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFileId() {
        return fileId;
    }

    public ProjectTaskFile fileId(Long fileId) {
        this.fileId = fileId;
        return this;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public ProjectTaskFile fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ProjectTaskFileType getType() {
        return type;
    }

    public ProjectTaskFile type(ProjectTaskFileType type) {
        this.type = type;
        return this;
    }

    public void setType(ProjectTaskFileType type) {
        this.type = type;
    }

    public Long getTaskId() {
        return taskId;
    }

    public ProjectTaskFile taskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectTaskFile)) {
            return false;
        }
        return id != null && id.equals(((ProjectTaskFile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectTaskFile{" +
            "id=" + getId() +
            ", fileId=" + getFileId() +
            ", fileName='" + getFileName() + "'" +
            ", type='" + getType() + "'" +
            ", taskId=" + getTaskId() +
            "}";
    }
}
