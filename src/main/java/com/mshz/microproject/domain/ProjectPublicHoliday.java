package com.mshz.microproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ProjectPublicHoliday.
 */
@Entity
@Table(name = "prj_public_holiday")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectPublicHoliday implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "level_id")
    private Long levelId;

    @Column(name = "process_id")
    private Long processId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ProjectPublicHoliday name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ProjectPublicHoliday date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getLevelId() {
        return levelId;
    }

    public ProjectPublicHoliday levelId(Long levelId) {
        this.levelId = levelId;
        return this;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public Long getProcessId() {
        return processId;
    }

    public ProjectPublicHoliday processId(Long processId) {
        this.processId = processId;
        return this;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectPublicHoliday)) {
            return false;
        }
        return id != null && id.equals(((ProjectPublicHoliday) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectPublicHoliday{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            ", levelId=" + getLevelId() +
            ", processId=" + getProcessId() +
            "}";
    }
}
