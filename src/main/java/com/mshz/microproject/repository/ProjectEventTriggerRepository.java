package com.mshz.microproject.repository;

import java.time.LocalDate;
import java.util.List;

import com.mshz.microproject.domain.ProjectEventTrigger;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectEventTrigger entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectEventTriggerRepository extends JpaRepository<ProjectEventTrigger, Long>, JpaSpecificationExecutor<ProjectEventTrigger> {

    List<ProjectEventTrigger> findBySheduledOnAndHourAndMinuteAndDisabledAndProcessIdIsNotNull(LocalDate localDate,
            int hour, int minute, Boolean false1);
}
