package spring.home6.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.home6.task.Task;
import spring.home6.task.TaskDto;


import java.util.List;

@RestController
@RequestMapping(value = "/api/task")
public class TaskApiController {


    private final TaskApiService taskApiService;
    private final UserApiService userApiService;

    @Autowired
    public TaskApiController(TaskApiService apiService, UserApiService userApiService) {
        this.taskApiService = apiService;
        this.userApiService = userApiService;
    }

    @GetMapping(value = "/all")
    public List<Task> getAllTask() {
        return taskApiService.getAllTask();
    }

    @GetMapping(value = "/{id}")
    public TaskDto getTaskById(@PathVariable Long id) {
        return taskApiService.getTaskById(id);
    }

    @PostMapping(value = "/")
    public TaskDto createTask(@RequestBody TaskDto task) {
        return taskApiService.createTask(task);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskApiService.deleteTask(id);
    }

    @PutMapping(value = "/{id}")
    public TaskDto updateTask(@PathVariable Long id, @RequestBody TaskDto task) {
        return taskApiService.updateTask(id, task);
    }

    @PostMapping("/{task_id}/user/{user_id}")
    public ResponseEntity<?> addUserToTask(@PathVariable Long task_id, @PathVariable Long user_id) {
        if(userApiService.getUserById(user_id) == null){
            return new ResponseEntity<>("User or user not found", HttpStatus.NOT_FOUND);
        }
        if (taskApiService.getTaskById(task_id) == null) {
            return new ResponseEntity<>("Task or user not found", HttpStatus.NOT_FOUND);
        }
        taskApiService.addUser(task_id,user_id);
        return new ResponseEntity<>(taskApiService.getTaskById(task_id), HttpStatus.OK);
    }
}

