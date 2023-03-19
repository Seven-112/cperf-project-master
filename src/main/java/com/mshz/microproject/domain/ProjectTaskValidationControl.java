package com.mshz.microproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ProjectTaskValidationControl.
 */
@Entity
@Table(name = "prj_task_validation_control")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectTaskValidationControl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "required")
    private Boolean required;

    @Column(name = "valid")
    private Boolean valid;

    @NotNull
    @Column(name = "task_id", nullable = false)
    private Long taskId;

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

    public ProjectTaskValidationControl label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isRequired() {
        return required;
    }

    public ProjectTaskValidationControl required(Boolean required) {
        this.required = required;
        return this;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean isValid() {
        return valid;
    }

    public ProjectTaskValidationControl valid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Long getTaskId() {
        return taskId;
    }

    public ProjectTaskValidationControl taskId(Long taskId) {
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
        if (!(o instanceof ProjectTaskValidationControl)) {
            return false;
        }
        return id != null && id.equals(((ProjectTaskValidationControl) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectTaskValidationControl{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", required='" + isRequired() + "'" +
            ", valid='" + isValid() + "'" +
            ", taskId=" + getTaskId() +
            "}";
    }
}
