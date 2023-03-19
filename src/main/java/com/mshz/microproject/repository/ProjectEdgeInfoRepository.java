package com.mshz.microproject.repository;

import java.util.List;

import com.mshz.microproject.domain.ProjectEdgeInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectEdgeInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectEdgeInfoRepository extends JpaRepository<ProjectEdgeInfo, Long>, JpaSpecificationExecutor<ProjectEdgeInfo> {

    @Modifying(flushAutomatically = true)
    @Query("delete ProjectEdgeInfo e where e.source != null and e.target != null and (e.source =:source or e.target =:target)")
    void deleteBySourceOrTarget(@Param("source") String source, @Param("target") String target);

    List<ProjectEdgeInfo> findBySource(String source);

    List<ProjectEdgeInfo> findByTarget(String target);

    List<ProjectEdgeInfo> findBySourceOrTarget(String source, String target);
}
