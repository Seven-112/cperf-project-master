package com.mshz.microproject.repository;

import com.mshz.microproject.domain.ProjectItemCheckJustificationFile;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectItemCheckJustificationFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectItemCheckJustificationFileRepository extends JpaRepository<ProjectItemCheckJustificationFile, Long>, JpaSpecificationExecutor<ProjectItemCheckJustificationFile> {
}
