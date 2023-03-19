package com.mshz.microproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ProjectItemCheckJustification.
 */
@Entity
@Table(name = "prj_item_check_justification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectItemCheckJustification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "checked")
    private Boolean checked;

    @NotNull
    @Column(name = "task_item_id", nullable = false)
    private Long taskItemId;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "justification")
    private String justification;

    @Column(name = "date_and_time")
    private Instant dateAndTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isChecked() {
        return checked;
    }

    public ProjectItemCheckJustification checked(Boolean checked) {
        this.checked = checked;
        return this;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Long getTaskItemId() {
        return taskItemId;
    }

    public ProjectItemCheckJustification taskItemId(Long taskItemId) {
        this.taskItemId = taskItemId;
        return this;
    }

    public void setTaskItemId(Long taskItemId) {
        this.taskItemId = taskItemId;
    }

    public String getJustification() {
        return justification;
    }

    public ProjectItemCheckJustification justification(String justification) {
        this.justification = justification;
        return this;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public Instant getDateAndTime() {
        return dateAndTime;
    }

    public ProjectItemCheckJustification dateAndTime(Instant dateAndTime) {
        this.dateAndTime = dateAndTime;
        return this;
    }

    public void setDateAndTime(Instant dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectItemCheckJustification)) {
            return false;
        }
        return id != null && id.equals(((ProjectItemCheckJustification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectItemCheckJustification{" +
            "id=" + getId() +
            ", checked='" + isChecked() + "'" +
            ", taskItemId=" + getTaskItemId() +
            ", justification='" + getJustification() + "'" +
            ", dateAndTime='" + getDateAndTime() + "'" +
            "}";
    }
}
