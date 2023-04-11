package ru.svlid.pp_312.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.svlid.pp_312.models.User;
import ru.svlid.pp_312.repos.UserRepo;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collections;


@Controller
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @GetMapping("/user")
    public String user (@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        Iterable<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "user";
    }

    @GetMapping("/user/add")
    public String userGetAdd (Model model) {
        return "add";
    }
    @PostMapping("/user/add")
    public String addUser(@RequestParam String name, @RequestParam String lastName, @RequestParam String email, Model model) {
        User user = new User(name, lastName, email);

        userRepo.save(user);

        Iterable<User> users = userRepo.findAll();
        model.addAttribute("users", users);

        return "redirect:/user";
    }

    @GetMapping("/user/{id}")
    public String addId (@PathVariable(value = "id") Long id, Model model) {

        if (!userRepo.existsById(id)) {
            return "user";
        }
        Iterable<User> user = userRepo.findAllById(Collections.singleton(id));
/*        Optional<User> user = userRepo.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);*/
        model.addAttribute("users", user);
        return "details";
    }

    @GetMapping("user/{id}/edit")
    public String edit(Model model, @PathVariable(value = "id") Long id) {
        if (!userRepo.existsById(id)) {
            return "redirect:/user";
        }
        Iterable<User> user = userRepo.findAllById(Collections.singleton(id));
        model.addAttribute("users", user);
        return "edit";
    }

    @PostMapping("user/{id}/edit")
    public String updateUser(@RequestParam String name, @RequestParam String lastName, @RequestParam String email,
                             @PathVariable Long id, Model model) {
        User user = userRepo.findById(id).orElseThrow();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        userRepo.save(user);
        return "redirect:/user";
    }
    @PostMapping("user/{id}/remove")
    public String removeUser(@PathVariable Long id, Model model) {
        User user = userRepo.findById(id).orElseThrow();
        userRepo.delete(user);
        return "redirect:/user";
    }
}
