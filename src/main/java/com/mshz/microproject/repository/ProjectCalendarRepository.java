package com.mshz.microproject.repository;

import java.time.Instant;
import java.util.List;

import com.mshz.microproject.domain.ProjectCalendar;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectCalendar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectCalendarRepository extends JpaRepository<ProjectCalendar, Long>, JpaSpecificationExecutor<ProjectCalendar> {

   /*  @Query("select pc from ProjectCalendar pc "
          +"where pc.projectId=:projectId and pc.dayNumber=:dayNumber "
          +"and :date between pc.startTime and pc.endTime ")
    List<ProjectCalendar> findByProjectIdAndDayNumberAndTimeBetweenStartAndEndTime(
            @Param("projectId") Long projectId, 
            @Param("dayNumber")  int dayNumber,@Param("date") Instant instant); */
    
   /*  @Query("select pc from ProjectCalendar pc "
      +"where pc.dayNumber=:dayNumber "
      +"and :date between pc.startTime and pc.endTime ")
    List<ProjectCalendar> findByDayNumberAndTimeBetweenStartAndEndTime( 
            @Param("dayNumber")  int dayNumber,@Param("date") Instant instant); */

    List<ProjectCalendar> findByDayNumber(Integer dayOfWeekValue);
}
