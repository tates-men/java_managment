package com.project.project.controller;

import com.project.project.models.Role;
import com.project.project.models.User;
import com.project.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    UserService userService;


    @GetMapping("/admin")
    public String adminPage(Model model){
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @PostMapping("/admin")
    public String giveRole(@AuthenticationPrincipal User user,
                           @RequestParam(required = true, defaultValue = "" ) Long userId,
                           @RequestParam(required = true, defaultValue = "" ) String action,
                           Model model){
        if(action.equals("delete")){
            User userr = userService.findUserById(userId);
            if(userr.getAdmin()){
                model.addAttribute("deleteError", "Нельзя удалить администратора!");
                return "admin";
            }
            else{
                userService.deleteUser(userId);
            }
        }

        if (action.equals("give_manager")) {
            User userr = userService.get(userId);
            userr.getRoles().add(new Role(2L, "ROLE_MANAGER"));
            userService.saveWith(userr);
        }

        if (action.equals("delete_role")) {
            User userr = userService.get(userId);
            userr.getRoles().clear();
            userr.getRoles().add(new Role(1L, "ROLE_CLIENT"));
            userService.saveWith(userr);
        }
        model.addAttribute("AllUsers", userService.allUsers());
        return "admin";
    }
}
