package spring.home6.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.home6.task.Task;

import java.util.List;

@RestController
@RequestMapping(value = "/api/task")
public class TaskApiController {


        private final TaskApiService service;

        @Autowired
        public TaskApiController(TaskApiService apiService){
            this.service = apiService;
        }

        @GetMapping(value = "/all")

        public List<Task> getAllTask() {
            return service.getAllTask();
        }
        @PostMapping
        public Task createTask(@RequestBody Task task){
          return   service.createTask(task);
        }

        @DeleteMapping("/id")
        public void deleteTask(@PathVariable Long id){
            service.deleteTask(id);
        }

        @PutMapping("/id")
        public Task updateTask(@PathVariable Long id,@RequestBody Task task){
            return service.updateTask(id,task);
        }



}
