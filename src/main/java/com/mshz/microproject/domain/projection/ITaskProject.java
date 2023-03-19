package com.mshz.microproject.domain.projection;

import com.mshz.microproject.domain.Project;
import com.mshz.microproject.domain.ProjectTask;

public interface ITaskProject {
    ProjectTask getTask();
    Project getProject();
}
