package com.mshz.microproject.domain.projection;

import java.time.Instant;

import com.mshz.microproject.domain.Project;

public class ProjectTimeLineGantt {
    private Long id;
    private Project project;
    private Instant startAt;
    private Instant maxFinishAt;

    public ProjectTimeLineGantt() {}

    public ProjectTimeLineGantt(Long id, Project project, Instant startAt, Instant maxFinishAt) {
        this.id = id;
        this.project = project;
        this.startAt = startAt;
        this.maxFinishAt = maxFinishAt;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Instant getStartAt() {
        return startAt;
    }

    public void setStartAt(Instant startAt) {
        this.startAt = startAt;
    }

    public Instant getMaxFinishAt() {
        return maxFinishAt;
    }

    public void setMaxFinishAt(Instant maxFinishAt) {
        this.maxFinishAt = maxFinishAt;
    }

    
    
}
