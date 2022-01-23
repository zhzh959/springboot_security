package com.gunt.springboot.springboot_security.controllers;

import com.gunt.springboot.springboot_security.dao.RoleDAO;
import com.gunt.springboot.springboot_security.entity.Role;
import com.gunt.springboot.springboot_security.entity.User;
import com.gunt.springboot.springboot_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class AdminController {


    private UserService userService;
    private final RoleDAO roleDAO;

    @Autowired
    public AdminController(UserService userService, RoleDAO roleDAO) {
        this.userService = userService;
        this.roleDAO = roleDAO;
    }

    @GetMapping("/admin")
    public ModelAndView showAllUsers() {
        List<User> users = userService.getAllUsers();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("all-users");
        modelAndView.addObject("allUsers", users);
        return modelAndView;
    }

    @GetMapping(value = "/admin/addNewUser")
    public String addPage() {
        return "user-info";
    }

    @PostMapping(value = "/admin/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/updateinfo/{id}")
    public ModelAndView editPage(@PathVariable("id") long id) {
        User user = userService.getById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("updateInfo");
        modelAndView.addObject("user", user);
        HashSet<Role> setroles = new HashSet<>();
        Role role_admin = roleDAO.createRoleIfNotFound("ADMIN", 1L);
        Role role_user = roleDAO.createRoleIfNotFound("USER", 2L);
        setroles.add(role_admin);
        setroles.add(role_user);
        modelAndView.addObject("rolelist", setroles);
        return modelAndView;
    }

    @PostMapping(value = "/admin/updateInfo")
    public String editUser(
            @ModelAttribute("id") Long id,
            @ModelAttribute("name") String name,
            @ModelAttribute("password") String password,
            @ModelAttribute("lastName") String lastName,
            @ModelAttribute("age") byte age,
            @RequestParam("roles") String[] roles
    ) {
        User user = userService.getById(id);
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);

        if (!password.isEmpty()) {
            user.setPassword(password);
        }
        Set<Role> setroles = new HashSet<>();
        for (String st : roles) {
            if (st.equals("ADMIN")) {
                Role role_admin = roleDAO.createRoleIfNotFound("ADMIN", 1L);
                setroles.add(role_admin);
            }
            if (st.equals("USER")) {
                Role role_user = roleDAO.createRoleIfNotFound("USER", 2L);
                setroles.add(role_user);
            }
        }
        user.setRoles(setroles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        User user = userService.getById(id);
        userService.deleteUser(user);
        return "redirect:/admin";
    }
}
