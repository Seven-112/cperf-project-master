package com.mshz.microproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.mshz.microproject.domain.enumeration.ProjectPriority;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority_level")
    private ProjectPriority priorityLevel;

    @Column(name = "valid")
    private Boolean valid;

    @Column(name = "preview_start_at")
    private Instant previewStartAt;

    @Column(name = "start_at")
    private Instant startAt;

    @Column(name = "preview_finish_at")
    private Instant previewFinishAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "start_count")
    private Integer startCount;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "editor_id")
    private Long editorId;

    @Column(name = "runnable_process_id")
    private Long runnableProcessId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "responsable_id")
    private Long responsableId;

    @Column(name = "responsable_name")
    private String responsableName;

    @Column(name = "responsable_email")
    private String responsableEmail;

    @Column(name = "ponderation")
    private Integer ponderation;

    @Column(name = "task_global_ponderation")
    private Integer taskGlobalPonderation;

    @Column(name = "path")
    private String path;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public Project label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public Project description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectPriority getPriorityLevel() {
        return priorityLevel;
    }

    public Project priorityLevel(ProjectPriority priorityLevel) {
        this.priorityLevel = priorityLevel;
        return this;
    }

    public void setPriorityLevel(ProjectPriority priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public Boolean isValid() {
        return valid;
    }

    public Project valid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Instant getPreviewStartAt() {
        return previewStartAt;
    }

    public Project previewStartAt(Instant previewStartAt) {
        this.previewStartAt = previewStartAt;
        return this;
    }

    public void setPreviewStartAt(Instant previewStartAt) {
        this.previewStartAt = previewStartAt;
    }

    public Instant getStartAt() {
        return startAt;
    }

    public Project startAt(Instant startAt) {
        this.startAt = startAt;
        return this;
    }

    public void setStartAt(Instant startAt) {
        this.startAt = startAt;
    }

    public Instant getPreviewFinishAt() {
        return previewFinishAt;
    }

    public Project previewFinishAt(Instant previewFinishAt) {
        this.previewFinishAt = previewFinishAt;
        return this;
    }

    public void setPreviewFinishAt(Instant previewFinishAt) {
        this.previewFinishAt = previewFinishAt;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }

    public Project finishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
        return this;
    }

    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Project createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getStartCount() {
        return startCount;
    }

    public Project startCount(Integer startCount) {
        this.startCount = startCount;
        return this;
    }

    public void setStartCount(Integer startCount) {
        this.startCount = startCount;
    }

    public Long getParentId() {
        return parentId;
    }

    public Project parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getEditorId() {
        return editorId;
    }

    public Project editorId(Long editorId) {
        this.editorId = editorId;
        return this;
    }

    public void setEditorId(Long editorId) {
        this.editorId = editorId;
    }

    public Long getRunnableProcessId() {
        return runnableProcessId;
    }

    public Project runnableProcessId(Long runnableProcessId) {
        this.runnableProcessId = runnableProcessId;
        return this;
    }

    public void setRunnableProcessId(Long runnableProcessId) {
        this.runnableProcessId = runnableProcessId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Project categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getResponsableId() {
        return responsableId;
    }

    public Project responsableId(Long responsableId) {
        this.responsableId = responsableId;
        return this;
    }

    public void setResponsableId(Long responsableId) {
        this.responsableId = responsableId;
    }

    public String getResponsableName() {
        return responsableName;
    }

    public Project responsableName(String responsableName) {
        this.responsableName = responsableName;
        return this;
    }

    public void setResponsableName(String responsableName) {
        this.responsableName = responsableName;
    }

    public String getResponsableEmail() {
        return responsableEmail;
    }

    public Project responsableEmail(String responsableEmail) {
        this.responsableEmail = responsableEmail;
        return this;
    }

    public void setResponsableEmail(String responsableEmail) {
        this.responsableEmail = responsableEmail;
    }

    public Integer getPonderation() {
        return ponderation;
    }

    public Project ponderation(Integer ponderation) {
        this.ponderation = ponderation;
        return this;
    }

    public void setPonderation(Integer ponderation) {
        this.ponderation = ponderation;
    }

    public Integer getTaskGlobalPonderation() {
        return taskGlobalPonderation;
    }

    public Project taskGlobalPonderation(Integer taskGlobalPonderation) {
        this.taskGlobalPonderation = taskGlobalPonderation;
        return this;
    }

    public void setTaskGlobalPonderation(Integer taskGlobalPonderation) {
        this.taskGlobalPonderation = taskGlobalPonderation;
    }

    public String getPath() {
        return path;
    }

    public Project path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", description='" + getDescription() + "'" +
            ", priorityLevel='" + getPriorityLevel() + "'" +
            ", valid='" + isValid() + "'" +
            ", previewStartAt='" + getPreviewStartAt() + "'" +
            ", startAt='" + getStartAt() + "'" +
            ", previewFinishAt='" + getPreviewFinishAt() + "'" +
            ", finishedAt='" + getFinishedAt() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", startCount=" + getStartCount() +
            ", parentId=" + getParentId() +
            ", editorId=" + getEditorId() +
            ", runnableProcessId=" + getRunnableProcessId() +
            ", categoryId=" + getCategoryId() +
            ", responsableId=" + getResponsableId() +
            ", responsableName='" + getResponsableName() + "'" +
            ", responsableEmail='" + getResponsableEmail() + "'" +
            ", ponderation=" + getPonderation() +
            ", taskGlobalPonderation=" + getTaskGlobalPonderation() +
            ", path='" + getPath() + "'" +
            "}";
    }
}
