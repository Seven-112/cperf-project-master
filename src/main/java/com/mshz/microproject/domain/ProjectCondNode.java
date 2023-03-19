package com.mshz.microproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ProjectCondNode.
 */
@Entity
@Table(name = "prj_cond_node")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectCondNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "logigram_pos_x")
    private Double logigramPosX;

    @Column(name = "logigram_pos_y")
    private Double logigramPosY;

    @Column(name = "project_id")
    private Long projectId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLogigramPosX() {
        return logigramPosX;
    }

    public ProjectCondNode logigramPosX(Double logigramPosX) {
        this.logigramPosX = logigramPosX;
        return this;
    }

    public void setLogigramPosX(Double logigramPosX) {
        this.logigramPosX = logigramPosX;
    }

    public Double getLogigramPosY() {
        return logigramPosY;
    }

    public ProjectCondNode logigramPosY(Double logigramPosY) {
        this.logigramPosY = logigramPosY;
        return this;
    }

    public void setLogigramPosY(Double logigramPosY) {
        this.logigramPosY = logigramPosY;
    }

    public Long getProjectId() {
        return projectId;
    }

    public ProjectCondNode projectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectCondNode)) {
            return false;
        }
        return id != null && id.equals(((ProjectCondNode) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectCondNode{" +
            "id=" + getId() +
            ", logigramPosX=" + getLogigramPosX() +
            ", logigramPosY=" + getLogigramPosY() +
            ", projectId=" + getProjectId() +
            "}";
    }
}
