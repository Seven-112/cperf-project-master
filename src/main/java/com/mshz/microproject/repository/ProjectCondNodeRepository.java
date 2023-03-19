package com.mshz.microproject.repository;

import com.mshz.microproject.domain.ProjectCondNode;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectCondNode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectCondNodeRepository extends JpaRepository<ProjectCondNode, Long>, JpaSpecificationExecutor<ProjectCondNode> {
}
