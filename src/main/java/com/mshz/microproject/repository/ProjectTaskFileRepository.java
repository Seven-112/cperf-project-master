package com.mshz.microproject.repository;

import java.util.List;

import com.mshz.microproject.domain.ProjectTaskFile;
import com.mshz.microproject.domain.enumeration.ProjectTaskFileType;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectTaskFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectTaskFileRepository extends JpaRepository<ProjectTaskFile, Long>, JpaSpecificationExecutor<ProjectTaskFile> {

    List<ProjectTaskFile> findByTaskIdAndType(Long taskModelId, ProjectTaskFileType taskFileType);

	@Modifying
	@Query("delete from ProjectTaskFile tf where tf.taskId=:tid")
    void deleteByTaskId(@Param("tid") Long id);
}
