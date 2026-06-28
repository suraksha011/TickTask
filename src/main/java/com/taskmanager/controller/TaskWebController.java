package com.taskmanager.controller;

import com.taskmanager.model.AppUser;
import com.taskmanager.model.Task;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskWebController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private AppUser currentUser(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("Logged-in user not found"));
    }

    @GetMapping("/")
    public String home(@RequestParam(value = "filter", required = false, defaultValue = "all") String filter,
                        Authentication authentication,
                        Model model) {

        AppUser user = currentUser(authentication);

        List<Task> tasks;
        switch (filter) {
            case "active":
                tasks = taskRepository.findByOwnerAndCompleted(user, false);
                break;
            case "completed":
                tasks = taskRepository.findByOwnerAndCompleted(user, true);
                break;
            default:
                tasks = taskRepository.findByOwner(user);
        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("newTask", new Task());
        model.addAttribute("filter", filter);
        model.addAttribute("priorities", Task.Priority.values());
        model.addAttribute("totalCount", tasks.size());
        model.addAttribute("currentUser", user);

        return "index";
    }

    @PostMapping("/tasks/add")
    public String addTask(@ModelAttribute Task newTask, Authentication authentication) {
        if (newTask.getTitle() != null && !newTask.getTitle().trim().isEmpty()) {
            newTask.setOwner(currentUser(authentication));
            taskRepository.save(newTask);
        }
        return "redirect:/";
    }

    @PostMapping("/tasks/{id}/toggle")
    public String toggleComplete(@PathVariable Long id, Authentication authentication) {
        AppUser user = currentUser(authentication);
        taskRepository.findById(id)
                .filter(task -> task.getOwner() != null && task.getOwner().getId().equals(user.getId()))
                .ifPresent(task -> {
                    task.setCompleted(!task.isCompleted());
                    taskRepository.save(task);
                });
        return "redirect:/";
    }

    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(@PathVariable Long id, Authentication authentication) {
        AppUser user = currentUser(authentication);
        taskRepository.findById(id)
                .filter(task -> task.getOwner() != null && task.getOwner().getId().equals(user.getId()))
                .ifPresent(task -> taskRepository.delete(task));
        return "redirect:/";
    }
}
