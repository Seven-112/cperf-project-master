package com.mshz.microproject.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.mshz.microproject.domain.ProjectPublicHoliday;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectPublicHoliday entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectPublicHolidayRepository extends JpaRepository<ProjectPublicHoliday, Long>, JpaSpecificationExecutor<ProjectPublicHoliday> {

    Optional<ProjectPublicHoliday> findByDateAndProcessId(LocalDate localDate, Long processId);

    Optional<ProjectPublicHoliday> findByDate(LocalDate localDate);

    List<ProjectPublicHoliday> findByDateBetween(LocalDate dateMin, LocalDate dateMax);
}
