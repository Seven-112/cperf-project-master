package com.mshz.microproject.repository;

import java.util.List;

import com.mshz.microproject.domain.ProjectTask;
import com.mshz.microproject.domain.ProjectTaskUser;
import com.mshz.microproject.domain.enumeration.ProjectTaskUserRole;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectTaskUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectTaskUserRepository extends JpaRepository<ProjectTaskUser, Long>, JpaSpecificationExecutor<ProjectTaskUser> {

    List<ProjectTaskUser> findByTaskId(Long id);

    Page<ProjectTaskUser> findByTaskIdAndRoleIn(Long taskId, List<ProjectTaskUserRole> roles, Pageable page);

	@Modifying
	@Query("delete from ProjectTaskUser tu where tu.taskId=:tid")
    void deleteByTaskId(@Param("tid") Long id);

    List<ProjectTaskUser> findByTaskIdAndRole(Long id, ProjectTaskUserRole exceutor);
}
