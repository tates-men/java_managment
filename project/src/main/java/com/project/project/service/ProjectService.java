package com.project.project.service;

import com.project.project.models.Project;
import com.project.project.models.Tasks;
import com.project.project.models.User;
import com.project.project.repository.ProjectRepository;
import com.project.project.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    TasksRepository tasksRepository;

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ProjectRepository projectRepository;

    public List<Tasks> allTasks(Long tasksId) {
        return tasksRepository.findByProjectId(tasksId);
    }
    public Project findProjectById(Long projectId) {
        Optional<Project> projectFromDb = projectRepository.findById(projectId);
        return projectFromDb.orElse(new Project());
    }

    public Tasks findTaskById(Long tasksId) {
        Optional<Tasks> taskFromDb = tasksRepository.findById(tasksId);
        return taskFromDb.orElse(new Tasks());
    }
}


