package com.project.project.controller;

import com.project.project.models.Project;
import com.project.project.models.Tasks;
import com.project.project.repository.ProjectRepository;
import com.project.project.repository.TasksRepository;
import com.project.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class RequestController {
    @Autowired
    TasksRepository tasksRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectService projectService;

    // GET-запрос на /projects
    @GetMapping("/request")
    public String getProjects(Model model) {
        model.addAttribute("allProject", projectRepository.findAll());
        return "request";
    }

    @PostMapping("/request")
    public String postRequest(@RequestParam(required = true, defaultValue = "" ) Long projectId,
                              @RequestParam(required = true, defaultValue = "" ) Long taskId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model){
        //Добавляем атрибут, который пропадает при пост запросе
        model.addAttribute("allProject", projectRepository.findAll());
        //Выводит задания по id проекта, который приходит на пост запрос
        if(action.equals("output")){
            model.addAttribute("allTasks", tasksRepository.findByProjectId(projectId));
        }
        //Если нажать на кнопку УДАЛИТЬ
        if(action.equals("delete")){
            tasksRepository.deleteById(taskId);
        }
        //Если нажать на кнопку В РАБОТУ
        if(action.equals("inWork")){
            Tasks tasks = projectService.findTaskById(taskId);
            tasks.setStatus("В работе");
            tasksRepository.save(tasks);
        }
        //Если нажать на кнопку ЗАВЕРШИТЬ
        if(action.equals("end")){
            Tasks tasks = projectService.findTaskById(taskId);
            tasks.setStatus("Завершено");
            tasksRepository.save(tasks);
        }

        if(action.equals("deleteProject")){
            List<Tasks> tasksDel = tasksRepository.findByProjectId(projectId);
            for (Tasks tasksDelete : tasksDel) {
                tasksRepository.deleteById(tasksDelete.getId());
            }
            projectRepository.deleteById(projectId);
            return "redirect:/request";
        }
        // Проверка на выполнение заданий
        List<Project> projects = projectRepository.findAll();
        for (Project project : projects) {
            List<Tasks> tasks = tasksRepository.findByProjectId(project.getId());
            boolean allCompleted = tasks.stream().allMatch(task -> task.getStatus().equals("Завершено"));

            if (allCompleted) {
                // Если все задания завершены, то меняем статус проекта на "завершено"
                Project project1 = projectService.findProjectById(project.getId());
                project1.setStatus("Завершено");
                projectRepository.save(project);
            }
        }

        return "request";
    }

}
