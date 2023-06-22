package com.project.project.controller;

import com.project.project.models.Project;
import com.project.project.models.Tasks;
import com.project.project.models.User;
import com.project.project.repository.ProjectRepository;
import com.project.project.repository.TasksRepository;
import com.project.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TasksRepository tasksRepository;
    @Autowired
    ProjectService projectService;

    @GetMapping("/")
    public String pageHome(@AuthenticationPrincipal User user, Model model){
        boolean hidden = true;
        boolean hidden1 = true;
        if(projectRepository.findByUserId(user.getId()) == null){
            model.addAttribute("projectForm", new Project());
            model.addAttribute("hidden1", hidden1);
        }
        else{
            model.addAttribute("hidden", hidden);
            Project project = projectRepository.findByUserId(user.getId());
            model.addAttribute("tasksForm", new Tasks());
            model.addAttribute("findProject", projectRepository.findByUserId(user.getId()));
            model.addAttribute("findByProject", projectService.allTasks(project.getId()));
        }

        return "homePage";
    }

    @PostMapping("/")
    public String postPage(@AuthenticationPrincipal User user,
                           @ModelAttribute("projectForm") Project project,
                           @ModelAttribute("tasksForm") Tasks tasks,
                           @RequestParam(required = true, defaultValue = "" ) String action,
                           Model model){
        if(action.equals("project")){
            project.setStatus("Создана");
            project.setUserId(user.getId());
            projectRepository.save(project);
            model.addAttribute("accessProject","Проект успешно создан!");
            boolean hidden = true;
            model.addAttribute("hidden", hidden);
            model.addAttribute("findProject", projectRepository.findByUserId(user.getId()));
            return "homePage";
        }

        if(action.equals("tasks")){
            Project project_add = projectRepository.findByUserId(user.getId());
            tasks.setStatus("Создана");
            tasks.setProjectId(project_add.getId());
            tasksRepository.save(tasks);
            model.addAttribute("findProject", projectRepository.findByUserId(user.getId()));
            model.addAttribute("findByProject", projectService.allTasks(project.getId()));
            return "redirect:/";
        }



        return "redirect:/";
    }
}
