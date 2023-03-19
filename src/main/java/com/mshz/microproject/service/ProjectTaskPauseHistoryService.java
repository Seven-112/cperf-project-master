package com.mshz.microproject.service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mshz.microproject.domain.ProjectTask;
import com.mshz.microproject.domain.ProjectTaskPauseHistory;
import com.mshz.microproject.domain.enumeration.ProjectTaskStatus;
import com.mshz.microproject.domain.projection.ChronoUtil;
import com.mshz.microproject.repository.ProjectTaskPauseHistoryRepository;

/**
 * Service Implementation for managing {@link ProjectTaskPauseHistory}.
 */
@Service
@Transactional
public class ProjectTaskPauseHistoryService {

	private final Logger log = LoggerFactory.getLogger(ProjectTaskPauseHistoryService.class);

	private final ProjectTaskPauseHistoryRepository projectTaskPauseHistoryRepository;

	public ProjectTaskPauseHistoryService(ProjectTaskPauseHistoryRepository projectTaskPauseHistoryRepository) {
		this.projectTaskPauseHistoryRepository = projectTaskPauseHistoryRepository;
	}

	/**
	 * Save a projectTaskPauseHistory.
	 *
	 * @param projectTaskPauseHistory the entity to save.
	 * @return the persisted entity.
	 */
	public ProjectTaskPauseHistory save(ProjectTaskPauseHistory projectTaskPauseHistory) {
		log.debug("Request to save ProjectTaskPauseHistory : {}", projectTaskPauseHistory);
		return projectTaskPauseHistoryRepository.save(projectTaskPauseHistory);
	}

	/**
	 * Get all the projectTaskPauseHistories.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<ProjectTaskPauseHistory> findAll(Pageable pageable) {
		log.debug("Request to get all ProjectTaskPauseHistories");
		return projectTaskPauseHistoryRepository.findAll(pageable);
	}

	/**
	 * Get one projectTaskPauseHistory by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<ProjectTaskPauseHistory> findOne(Long id) {
		log.debug("Request to get ProjectTaskPauseHistory : {}", id);
		return projectTaskPauseHistoryRepository.findById(id);
	}

	/**
	 * Delete the projectTaskPauseHistory by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete ProjectTaskPauseHistory : {}", id);
		projectTaskPauseHistoryRepository.deleteById(id);
	}

	public ProjectTaskPauseHistory trackTaskPause(ProjectTask task, Instant date) {
		ProjectTaskPauseHistory history = new ProjectTaskPauseHistory();
		if (task != null) {
			history.setTaskId(task.getId());
			history.setOldTaskstatus(task.getStatus());
			history.setPausedAt(date != null ? date : Instant.now());
			// ChronoUtil chronoUtil = getTaskChrono(task);
			// history.setTaskExecutionDeleyExeceed(chronoUtil.isExeceed());
			history.setTaskExecutionDeleyExeceed(null);
			history.setRestartedAt(null);
			return projectTaskPauseHistoryRepository.save(history);
		}
		return history;
	}

	public ProjectTaskPauseHistory updateRestartedAt(Long id) {
		Optional<ProjectTaskPauseHistory> historyOpt = projectTaskPauseHistoryRepository.findById(id);
		if (historyOpt.isPresent()) {
			ProjectTaskPauseHistory history = historyOpt.get();
			history.setRestartedAt(Instant.now());
			return projectTaskPauseHistoryRepository.save(history);
		}
		return null;
	}

	public ChronoUtil getTaskChrono(ProjectTask task) {
		if (task != null && task.getStartAt() != null) {
			// calcul max finish date
			ZonedDateTime zdt = ZonedDateTime.ofInstant(task.getStartAt(), ZoneId.systemDefault())
					.plusYears(task.getNbYears() != null ? task.getNbYears().longValue() : 0)
					.plusMonths(task.getNbMonths() != null ? task.getNbMonths().longValue() : 0)
					.plusDays(task.getNbDays() != null ? task.getNbDays().longValue() : 0)
					.plusHours(task.getNbHours() != null ? task.getNbHours().longValue() : 0)
					.plusMinutes(task.getNbMinuites() != null ? task.getNbMinuites().longValue() : 0)
					// .plusSeconds(calculeElapsedSecondsInTaskPause(task.getId(), Boolean.FALSE))
					// .plusSeconds(calculeElapsedSecondsInTaskPause(task.getId(), Boolean.TRUE));
					.plusSeconds(calculeAllPausedTimes(task.getId()));

			Instant previewFinishDate = zdt.toInstant();

			boolean execeed = (task.getStatus() == ProjectTaskStatus.COMPLETED && task.getFinishAt() != null)
					? previewFinishDate.isBefore(task.getFinishAt())
					: previewFinishDate.isBefore(Instant.now());

			return new ChronoUtil(task.getStartAt(), task.getPauseAt(), previewFinishDate, task.getFinishAt(),
					task.getStatus(), execeed);

		}
		return null;
	}

	public long calculeAllPausedTimes(Long taskId) {
		long seconds = 0;
		Pageable pageable = PageRequest.of(0, 200);
		while (pageable != null) {
			Page<ProjectTaskPauseHistory> page = projectTaskPauseHistoryRepository
					.findByTaskIdAndPausedAtNotNullAndRestartedAtNotNull(taskId, pageable);
			for (ProjectTaskPauseHistory history : page.getContent()) {
				seconds = seconds + Duration.between(history.getPausedAt(), history.getRestartedAt()).abs().toSeconds();
			}
			// increment or breack loop by setting pageable to null
			pageable = page.hasNext() ? page.nextPageable() : null;
		}
		return seconds;
	}

	public long calculeElapsedSecondsInTaskPause(Long taskId, Boolean executionTimeExeceed) {
		long seconds = 0;
		List<ProjectTaskPauseHistory> histories = projectTaskPauseHistoryRepository
				.findByTaskIdAndTaskExecutionDeleyExeceedAndPausedAtNotNullAndRestartedAtNotNull(taskId,
						executionTimeExeceed);
		for (ProjectTaskPauseHistory history : histories) {
			seconds = seconds + Duration.between(history.getPausedAt(), history.getRestartedAt()).abs().toSeconds();
		}
		return seconds;
	}

	public void deleteByTaskId(Long taskId) {
		projectTaskPauseHistoryRepository.deleteByTaskId(taskId);
	}
}
