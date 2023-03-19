package com.mshz.microproject.repository;

import com.mshz.microproject.domain.ProjectCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectCategoryRepository extends JpaRepository<ProjectCategory, Long>, JpaSpecificationExecutor<ProjectCategory> {
}
