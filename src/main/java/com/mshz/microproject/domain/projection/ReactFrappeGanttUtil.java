package com.mshz.microproject.domain.projection;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mshz.microproject.domain.Project;
import com.mshz.microproject.domain.ProjectTask;
import com.mshz.microproject.domain.enumeration.ProjectTaskStatus;

public class ReactFrappeGanttUtil implements Serializable, Cloneable{
    protected Long id;
    protected ProjectTask task; // project or task Id
    protected Project project;
    protected ChronoUtil chronoUtil;
    protected Instant startDate;
    protected Instant endDate;
    protected Float progress;
    protected List<Long> depends = new ArrayList<>();
    protected boolean editable;
    protected boolean isProject;

    /**
     * constructor to instance task gantt data util
     * @param task
     * @param chronoUtil
     */
    public ReactFrappeGanttUtil(ProjectTask task, ChronoUtil chronoUtil) {
        this.task = task;
        this.chronoUtil = chronoUtil;
        this.editable = false;
        this.isProject = false;
        if(task != null)
            this.id = task.getId();
        setStartDate(Instant.now());
    }

    /**
     * constructor to initialize project gantt data utils
     * @param project
     * @param chronoUtil
     * @param tasks
     */
    public ReactFrappeGanttUtil(Project project, ChronoUtil chronoUtil, List<ProjectTask> tasks) {
        this.project = project;
        this.chronoUtil = chronoUtil;
        this.isProject = true;
        setStartAndFinishDates(tasks);
        if(project != null)
            this.id = project.getId();
        this.editable = false;
    }
    
    /**
     * constructor of copy
     * @param copy
     */
    public ReactFrappeGanttUtil(ReactFrappeGanttUtil copy) {
    	this.id = copy.id;
        this.project = copy.project;
        this.chronoUtil = copy.chronoUtil;
        this.isProject = copy.isProject;
        this.task = copy.task;
        this.startDate = copy.startDate;
        this.endDate = copy.endDate;
        this.progress = copy.progress;
        this.depends = copy.depends;
        this.editable = copy.editable;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public ProjectTask getTask() {
        return this.task;
    }

    public void setTask(ProjectTask task) {
        this.task = task;
        if(task != null)
            this.id = task.getId();
        setStartDate(Instant.now());
    }

    public ChronoUtil getChronoUtil() {
        return chronoUtil;
    }

    public void setChronoUtil(ChronoUtil chronoUtil) {
        this.chronoUtil = chronoUtil;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Instant defaultDate) {
        if(this.task != null){
            if(this.task.getStartAt() != null){
                this.startDate = this.task.getStartAt();
            }else if(this.task.getSheduledStartAt() != null){
                ZonedDateTime zdt = this.task.getSheduledStartAt().atStartOfDay(ZoneId.systemDefault());
                if(this.task.getSheduledStartHour() != null)
                    zdt = zdt.withHour(this.task.getSheduledStartHour().intValue());
                if(this.task.getSheduledStartMinute() != null)
                    zdt = zdt.withMinute(this.task.getSheduledStartMinute().intValue());
                this.startDate = zdt.toInstant();
            }else if(defaultDate != null){
                this.startDate = defaultDate;
            }else if(this.task.getCreatedAt() != null){
                this.startDate = this.task.getCreatedAt();
            }else{
                // gantt nécessite obligatoire une start datae
                this.startDate = Instant.now();
            }
        }else{
            this.startDate = defaultDate != null ? defaultDate : Instant.now();
        }
        setEndDate();
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public void setEndDate() {
        if(this.startDate != null){
            if(this.task != null){
                ZonedDateTime zdt = this.startDate.atZone(ZoneId.systemDefault());
                if(this.task.getNbYears() != null)
                    zdt = zdt.plusYears(this.task.getNbYears().longValue());
                if(this.task.getNbMonths() != null)
                    zdt = zdt.plusMonths(this.task.getNbMonths().longValue());
                if(this.task.getNbDays() != null)
                    zdt = zdt.plusDays(this.task.getNbDays().longValue());
                if(this.task.getNbHours() != null)
                    zdt = zdt.plusHours(this.task.getNbHours().longValue());
                if(this.task.getNbMinuites() != null)
                    zdt = zdt.plusMinutes(this.task.getNbMinuites().longValue());
                this.endDate = zdt.toInstant();   
            }else{
                this.endDate = this.startDate;
            }
        }
        setProgress();
    }

    public Float getProgress() {
        return this.progress;
    }

    protected void setProgress() {
        if(this.task != null && this.task.getStatus() != ProjectTaskStatus.VALID){
            if(this.startDate != null && this.endDate != null){
                if(this.startDate.equals(this.endDate) || Instant.now().isAfter(this.endDate)){
                    this.progress = Float.valueOf(100);
                }else{
                    Instant minDate, maxDate = null;
                    if(this.endDate.isAfter(this.startDate)){
                        maxDate = this.endDate;
                        minDate = this.startDate;
                    }else{
                        maxDate = this.startDate;
                        minDate = this.endDate;
                    }
                    Duration maxDuration = Duration.between(minDate, maxDate);
                    Duration progressDuration = Duration.between(minDate, Instant.now());
                    if(maxDuration != null && progressDuration != null){
                        this.progress = Float.valueOf(Math.abs((progressDuration.toMillis() * 100) / maxDuration.toMillis()));
                    }
                }
            }else{
                this.progress = Float.valueOf(0);
            }
        }else{
            this.progress = Float.valueOf(0);
        }
    }


    public void setProgress(Float progress) {
        this.progress = progress;
    }

    public List<Long> getDepends() {
        return this.depends;
    }

    public void setDepends(List<Long> depends) {
        this.depends = depends;
        if(depends != null && !depends.isEmpty())
            this.editable = false;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void setPreviewTaskFinishAt(Instant previewTaskFinishAt) {
        setStartDate(previewTaskFinishAt);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public boolean isProject() {
        return isProject;
    }

    public void setProject(boolean isProject) {
        this.isProject = isProject;
    }


    private void setStartAndFinishDates(List<ProjectTask> tasks){
        if(tasks != null && !tasks.isEmpty() && chronoUtil == null){
            // finding by start Date
            List<Instant> instans = tasks.stream()
                        .filter(t -> t.getStartAt() != null)
                        .map(t -> t.getStartAt()).collect(Collectors.toList());
            if(instans == null || instans.isEmpty()){
               // finding by sheduled start date
               instans = tasks.stream()
                        .filter(t -> t.getSheduledStartAt() != null)
                        .map(t -> t.getSheduledStartAt().atStartOfDay(ZoneId.systemDefault())
                        .withHour(t.getSheduledStartHour() == null ? 0 : t.getSheduledStartHour().intValue())
                        .withMinute(t.getSheduledStartMinute() == null ? 1 : t.getSheduledStartMinute().intValue())
                        .toInstant()).collect(Collectors.toList());;
            }
            if(instans == null || instans.isEmpty()){
                // finding by created date
                instans = tasks.stream()
                            .filter(t -> t.getCreatedAt() != null)
                            .map(t -> t.getCreatedAt())
                            .collect(Collectors.toList());
            }
            if(instans != null && !instans.isEmpty()){
                this.startDate = instans.stream().min(Instant::compareTo).orElse(Instant.now());
                this.endDate = instans.stream().max(Instant::compareTo).orElse(Instant.now());
            }

            if(this.startDate == null)
                this.startDate = Instant.now();
            if(this.endDate == null)
                this.endDate = Instant.now();
        }else{
            if(chronoUtil != null){
                this.startDate = chronoUtil.getStartDate();
                this.endDate = chronoUtil.getFinishDate() != null
                               ? chronoUtil.getFinishDate()
                               : chronoUtil.getPrviewFinishDate();
            }
        }

        if(this.startDate == null)
            this.startDate = Instant.now();
        if(this.endDate == null)
            this.endDate = this.startDate;
        
        // pour un peut de décalage à l'affichage entre la première tâche la dérnière    
        this.startDate = this.startDate.atZone(ZoneId.systemDefault())
                            .minusHours(1).toInstant();
        this.endDate = this.endDate.atZone(ZoneId.systemDefault())
                            .plusHours(1).toInstant();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return  super.clone();
    }
}