package com.mshz.microproject.repository;

import java.util.List;

import com.mshz.microproject.domain.Project;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Project entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    List<Project> findByValid(Boolean true1);

    List<Project> findByParentIdAndValid(Long projectId, Boolean valid);

    List<Project> findByPathContainingIgnoreCaseOrIdIn(String path, List<Long> ids);

    @Query("select p from Project p where p.path=:path or p.path like :startWith% order by path")
    List<Project> findByPathEqualsOrStartWith(@Param("path") String path, @Param("startWith") String startWith);

    List<Project> findByParentIdAndValidOrderByPath(Long projectId, Boolean valid);
}
