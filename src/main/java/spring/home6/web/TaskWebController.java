package spring.home6.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.home6.task.Task;

import java.util.Date;

@Controller

public class TaskWebController {

    private final TaskWebService service;

    @Autowired
    public TaskWebController(TaskWebService taskWebService) {
        this.service = taskWebService;
    }

    @GetMapping("/create")
    public String createTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "GetCreateTask";
    }


    @PostMapping("/create")
    public String postNewTask(@ModelAttribute Task task, Model model) {
        Task currtask = service.createTask(task.getName(), task.getDescription());
        model.addAttribute("task", currtask);
        return "PostCreateTask";
    }

    @GetMapping("/dropTable")
    @ResponseBody
    public String dropTables() {
        service.dropTables();
        return "Tables dropped successfully";
    }

    @GetMapping("/allTasks")
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", service.getAllTasks());
        return "GetAllTasks";
    }

    @PostMapping("/editTask")
    public String editTask(@RequestParam Long id, Model model) {
        Task currentTask = service.getTaskById(id);
        model.addAttribute("task", currentTask);
        return "EditTask";
    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task, Model model) {
        Task currentTask = service.getTaskById(task.getId());
        if (currentTask != null) {
            service.updateTask(task.getId(), task);
            model.addAttribute("task", currentTask);
            return "PostCreateTask";
        }
        return "BadRequest";
    }

    @PostMapping("/delTask")
    @ResponseBody
    public String delTask(@RequestParam Long id) {
        service.deleteTask(id);
        return String.format("%d Была удалена", id);
    }
}