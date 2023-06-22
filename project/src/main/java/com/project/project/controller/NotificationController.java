package com.project.project.controller;

import com.project.project.models.Project;
import com.project.project.models.User;
import com.project.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotificationController {

    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/notification")
    public String getPage(@AuthenticationPrincipal User user, Model model){
        Project project = projectRepository.findByUserId(user.getId());
        if(project != null){
            if(project.getStatus().equals("Завершено")) {
                model.addAttribute("projectFind", projectRepository.findByUserId(user.getId()));
            }
        }
        else{
            model.addAttribute("notFound","Ваш проект не завершен или еще не создан");
        }
        return "notification";
    }

}
