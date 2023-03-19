package com.mshz.microproject.repository;

import com.mshz.microproject.domain.ProjectItemCheckJustification;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectItemCheckJustification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectItemCheckJustificationRepository extends JpaRepository<ProjectItemCheckJustification, Long>, JpaSpecificationExecutor<ProjectItemCheckJustification> {
}
