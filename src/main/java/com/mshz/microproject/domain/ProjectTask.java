package com.mshz.microproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import com.mshz.microproject.domain.enumeration.ProjectTaskStatus;

import com.mshz.microproject.domain.enumeration.ProjectPriority;

import com.mshz.microproject.domain.enumeration.ProjectTaskType;

/**
 * A ProjectTask.
 */
@Entity
@Table(name = "prj_task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Column(name = "nb_minuites")
    private Integer nbMinuites;

    @Column(name = "nb_hours")
    private Integer nbHours;

    @Column(name = "nb_days")
    private Integer nbDays;

    @Column(name = "nb_months")
    private Integer nbMonths;

    @Column(name = "nb_years")
    private Integer nbYears;

    @Column(name = "start_at")
    private Instant startAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProjectTaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority_level")
    private ProjectPriority priorityLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ProjectTaskType type;

    @Column(name = "valid")
    private Boolean valid;

    @Column(name = "finish_at")
    private Instant finishAt;

    @Column(name = "start_with_process")
    private Boolean startWithProcess;

    @Column(name = "process_id")
    private Long processId;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "task_model_id")
    private Long taskModelId;

    @Column(name = "pause_at")
    private Instant pauseAt;

    @Column(name = "nb_pause")
    private Integer nbPause;

    @Column(name = "logigram_pos_x")
    private Double logigramPosX;

    @Column(name = "logigram_pos_y")
    private Double logigramPosY;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "risk_id")
    private Long riskId;

    @Column(name = "manual_mode")
    private Boolean manualMode;

    @Column(name = "sheduled_start_at")
    private LocalDate sheduledStartAt;

    @Column(name = "sheduled_start_hour")
    private Integer sheduledStartHour;

    @Column(name = "sheduled_start_minute")
    private Integer sheduledStartMinute;

    @Column(name = "startup_task_id")
    private Long startupTaskId;

    @Column(name = "ponderation")
    private Integer ponderation;

    @Column(name = "checked")
    private Boolean checked;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "current_pause_history_id")
    private Long currentPauseHistoryId;

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

    public ProjectTask name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ProjectTask description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNbMinuites() {
        return nbMinuites;
    }

    public ProjectTask nbMinuites(Integer nbMinuites) {
        this.nbMinuites = nbMinuites;
        return this;
    }

    public void setNbMinuites(Integer nbMinuites) {
        this.nbMinuites = nbMinuites;
    }

    public Integer getNbHours() {
        return nbHours;
    }

    public ProjectTask nbHours(Integer nbHours) {
        this.nbHours = nbHours;
        return this;
    }

    public void setNbHours(Integer nbHours) {
        this.nbHours = nbHours;
    }

    public Integer getNbDays() {
        return nbDays;
    }

    public ProjectTask nbDays(Integer nbDays) {
        this.nbDays = nbDays;
        return this;
    }

    public void setNbDays(Integer nbDays) {
        this.nbDays = nbDays;
    }

    public Integer getNbMonths() {
        return nbMonths;
    }

    public ProjectTask nbMonths(Integer nbMonths) {
        this.nbMonths = nbMonths;
        return this;
    }

    public void setNbMonths(Integer nbMonths) {
        this.nbMonths = nbMonths;
    }

    public Integer getNbYears() {
        return nbYears;
    }

    public ProjectTask nbYears(Integer nbYears) {
        this.nbYears = nbYears;
        return this;
    }

    public void setNbYears(Integer nbYears) {
        this.nbYears = nbYears;
    }

    public Instant getStartAt() {
        return startAt;
    }

    public ProjectTask startAt(Instant startAt) {
        this.startAt = startAt;
        return this;
    }

    public void setStartAt(Instant startAt) {
        this.startAt = startAt;
    }

    public ProjectTaskStatus getStatus() {
        return status;
    }

    public ProjectTask status(ProjectTaskStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProjectTaskStatus status) {
        this.status = status;
    }

    public ProjectPriority getPriorityLevel() {
        return priorityLevel;
    }

    public ProjectTask priorityLevel(ProjectPriority priorityLevel) {
        this.priorityLevel = priorityLevel;
        return this;
    }

    public void setPriorityLevel(ProjectPriority priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public ProjectTaskType getType() {
        return type;
    }

    public ProjectTask type(ProjectTaskType type) {
        this.type = type;
        return this;
    }

    public void setType(ProjectTaskType type) {
        this.type = type;
    }

    public Boolean isValid() {
        return valid;
    }

    public ProjectTask valid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Instant getFinishAt() {
        return finishAt;
    }

    public ProjectTask finishAt(Instant finishAt) {
        this.finishAt = finishAt;
        return this;
    }

    public void setFinishAt(Instant finishAt) {
        this.finishAt = finishAt;
    }

    public Boolean isStartWithProcess() {
        return startWithProcess;
    }

    public ProjectTask startWithProcess(Boolean startWithProcess) {
        this.startWithProcess = startWithProcess;
        return this;
    }

    public void setStartWithProcess(Boolean startWithProcess) {
        this.startWithProcess = startWithProcess;
    }

    public Long getProcessId() {
        return processId;
    }

    public ProjectTask processId(Long processId) {
        this.processId = processId;
        return this;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Long getParentId() {
        return parentId;
    }

    public ProjectTask parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getTaskModelId() {
        return taskModelId;
    }

    public ProjectTask taskModelId(Long taskModelId) {
        this.taskModelId = taskModelId;
        return this;
    }

    public void setTaskModelId(Long taskModelId) {
        this.taskModelId = taskModelId;
    }

    public Instant getPauseAt() {
        return pauseAt;
    }

    public ProjectTask pauseAt(Instant pauseAt) {
        this.pauseAt = pauseAt;
        return this;
    }

    public void setPauseAt(Instant pauseAt) {
        this.pauseAt = pauseAt;
    }

    public Integer getNbPause() {
        return nbPause;
    }

    public ProjectTask nbPause(Integer nbPause) {
        this.nbPause = nbPause;
        return this;
    }

    public void setNbPause(Integer nbPause) {
        this.nbPause = nbPause;
    }

    public Double getLogigramPosX() {
        return logigramPosX;
    }

    public ProjectTask logigramPosX(Double logigramPosX) {
        this.logigramPosX = logigramPosX;
        return this;
    }

    public void setLogigramPosX(Double logigramPosX) {
        this.logigramPosX = logigramPosX;
    }

    public Double getLogigramPosY() {
        return logigramPosY;
    }

    public ProjectTask logigramPosY(Double logigramPosY) {
        this.logigramPosY = logigramPosY;
        return this;
    }

    public void setLogigramPosY(Double logigramPosY) {
        this.logigramPosY = logigramPosY;
    }

    public Long getGroupId() {
        return groupId;
    }

    public ProjectTask groupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getRiskId() {
        return riskId;
    }

    public ProjectTask riskId(Long riskId) {
        this.riskId = riskId;
        return this;
    }

    public void setRiskId(Long riskId) {
        this.riskId = riskId;
    }

    public Boolean isManualMode() {
        return manualMode;
    }

    public ProjectTask manualMode(Boolean manualMode) {
        this.manualMode = manualMode;
        return this;
    }

    public void setManualMode(Boolean manualMode) {
        this.manualMode = manualMode;
    }

    public LocalDate getSheduledStartAt() {
        return sheduledStartAt;
    }

    public ProjectTask sheduledStartAt(LocalDate sheduledStartAt) {
        this.sheduledStartAt = sheduledStartAt;
        return this;
    }

    public void setSheduledStartAt(LocalDate sheduledStartAt) {
        this.sheduledStartAt = sheduledStartAt;
    }

    public Integer getSheduledStartHour() {
        return sheduledStartHour;
    }

    public ProjectTask sheduledStartHour(Integer sheduledStartHour) {
        this.sheduledStartHour = sheduledStartHour;
        return this;
    }

    public void setSheduledStartHour(Integer sheduledStartHour) {
        this.sheduledStartHour = sheduledStartHour;
    }

    public Integer getSheduledStartMinute() {
        return sheduledStartMinute;
    }

    public ProjectTask sheduledStartMinute(Integer sheduledStartMinute) {
        this.sheduledStartMinute = sheduledStartMinute;
        return this;
    }

    public void setSheduledStartMinute(Integer sheduledStartMinute) {
        this.sheduledStartMinute = sheduledStartMinute;
    }

    public Long getStartupTaskId() {
        return startupTaskId;
    }

    public ProjectTask startupTaskId(Long startupTaskId) {
        this.startupTaskId = startupTaskId;
        return this;
    }

    public void setStartupTaskId(Long startupTaskId) {
        this.startupTaskId = startupTaskId;
    }

    public Integer getPonderation() {
        return ponderation;
    }

    public ProjectTask ponderation(Integer ponderation) {
        this.ponderation = ponderation;
        return this;
    }

    public void setPonderation(Integer ponderation) {
        this.ponderation = ponderation;
    }

    public Boolean isChecked() {
        return checked;
    }

    public ProjectTask checked(Boolean checked) {
        this.checked = checked;
        return this;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public ProjectTask createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCurrentPauseHistoryId() {
        return currentPauseHistoryId;
    }

    public ProjectTask currentPauseHistoryId(Long currentPauseHistoryId) {
        this.currentPauseHistoryId = currentPauseHistoryId;
        return this;
    }

    public void setCurrentPauseHistoryId(Long currentPauseHistoryId) {
        this.currentPauseHistoryId = currentPauseHistoryId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectTask)) {
            return false;
        }
        return id != null && id.equals(((ProjectTask) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectTask{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", nbMinuites=" + getNbMinuites() +
            ", nbHours=" + getNbHours() +
            ", nbDays=" + getNbDays() +
            ", nbMonths=" + getNbMonths() +
            ", nbYears=" + getNbYears() +
            ", startAt='" + getStartAt() + "'" +
            ", status='" + getStatus() + "'" +
            ", priorityLevel='" + getPriorityLevel() + "'" +
            ", type='" + getType() + "'" +
            ", valid='" + isValid() + "'" +
            ", finishAt='" + getFinishAt() + "'" +
            ", startWithProcess='" + isStartWithProcess() + "'" +
            ", processId=" + getProcessId() +
            ", parentId=" + getParentId() +
            ", taskModelId=" + getTaskModelId() +
            ", pauseAt='" + getPauseAt() + "'" +
            ", nbPause=" + getNbPause() +
            ", logigramPosX=" + getLogigramPosX() +
            ", logigramPosY=" + getLogigramPosY() +
            ", groupId=" + getGroupId() +
            ", riskId=" + getRiskId() +
            ", manualMode='" + isManualMode() + "'" +
            ", sheduledStartAt='" + getSheduledStartAt() + "'" +
            ", sheduledStartHour=" + getSheduledStartHour() +
            ", sheduledStartMinute=" + getSheduledStartMinute() +
            ", startupTaskId=" + getStartupTaskId() +
            ", ponderation=" + getPonderation() +
            ", checked='" + isChecked() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", currentPauseHistoryId=" + getCurrentPauseHistoryId() +
            "}";
    }
}
