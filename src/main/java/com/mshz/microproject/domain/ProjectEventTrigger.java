package com.mshz.microproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import com.mshz.microproject.domain.enumeration.ProjectEventRecurrence;

/**
 * A ProjectEventTrigger.
 */
@Entity
@Table(name = "prj_event_trigger")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectEventTrigger implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "editor_id", nullable = false)
    private Long editorId;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence")
    private ProjectEventRecurrence recurrence;

    @Column(name = "disabled")
    private Boolean disabled;

    @Column(name = "editor_name")
    private String editorName;

    @Column(name = "hour")
    private Integer hour;

    @Column(name = "minute")
    private Integer minute;

    @Column(name = "first_started_at")
    private Instant firstStartedAt;

    @Column(name = "sheduled_on")
    private LocalDate sheduledOn;

    @Column(name = "process_id")
    private Long processId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEditorId() {
        return editorId;
    }

    public ProjectEventTrigger editorId(Long editorId) {
        this.editorId = editorId;
        return this;
    }

    public void setEditorId(Long editorId) {
        this.editorId = editorId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public ProjectEventTrigger createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public ProjectEventTrigger name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectEventRecurrence getRecurrence() {
        return recurrence;
    }

    public ProjectEventTrigger recurrence(ProjectEventRecurrence recurrence) {
        this.recurrence = recurrence;
        return this;
    }

    public void setRecurrence(ProjectEventRecurrence recurrence) {
        this.recurrence = recurrence;
    }

    public Boolean isDisabled() {
        return disabled;
    }

    public ProjectEventTrigger disabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getEditorName() {
        return editorName;
    }

    public ProjectEventTrigger editorName(String editorName) {
        this.editorName = editorName;
        return this;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public Integer getHour() {
        return hour;
    }

    public ProjectEventTrigger hour(Integer hour) {
        this.hour = hour;
        return this;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public ProjectEventTrigger minute(Integer minute) {
        this.minute = minute;
        return this;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Instant getFirstStartedAt() {
        return firstStartedAt;
    }

    public ProjectEventTrigger firstStartedAt(Instant firstStartedAt) {
        this.firstStartedAt = firstStartedAt;
        return this;
    }

    public void setFirstStartedAt(Instant firstStartedAt) {
        this.firstStartedAt = firstStartedAt;
    }

    public LocalDate getSheduledOn() {
        return sheduledOn;
    }

    public ProjectEventTrigger sheduledOn(LocalDate sheduledOn) {
        this.sheduledOn = sheduledOn;
        return this;
    }

    public void setSheduledOn(LocalDate sheduledOn) {
        this.sheduledOn = sheduledOn;
    }

    public Long getProcessId() {
        return processId;
    }

    public ProjectEventTrigger processId(Long processId) {
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
        if (!(o instanceof ProjectEventTrigger)) {
            return false;
        }
        return id != null && id.equals(((ProjectEventTrigger) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectEventTrigger{" +
            "id=" + getId() +
            ", editorId=" + getEditorId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", name='" + getName() + "'" +
            ", recurrence='" + getRecurrence() + "'" +
            ", disabled='" + isDisabled() + "'" +
            ", editorName='" + getEditorName() + "'" +
            ", hour=" + getHour() +
            ", minute=" + getMinute() +
            ", firstStartedAt='" + getFirstStartedAt() + "'" +
            ", sheduledOn='" + getSheduledOn() + "'" +
            ", processId=" + getProcessId() +
            "}";
    }
}
