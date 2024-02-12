package spring.home6.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.home6.task.Task;
import spring.home6.user.User;

import java.util.List;

@RestController
@RequestMapping(value = "/api/task")
public class TaskApiController {


        private final TaskApiService taslApiservice;
        private final UserApiService userApiService;

        @Autowired
        public TaskApiController(TaskApiService apiService,UserApiService userApiService){
            this.taslApiservice = apiService;
            this.userApiService = userApiService;
        }

        @GetMapping(value = "/all")

        public List<Task> getAllTask() {
            return taslApiservice.getAllTask();
        }
        @PostMapping
        public Task createTask(@RequestBody Task task){
          return   taslApiservice.createTask(task);
        }

        @DeleteMapping("/id")
        public void deleteTask(@PathVariable Long id){
            taslApiservice.deleteTask(id);
        }

        @PutMapping("/id")
        public Task updateTask(@PathVariable Long id,@RequestBody Task task){
            return taslApiservice.updateTask(id,task);
        }

        @PutMapping("/task/{task_id}/user/{user_id}")
        public ResponseEntity<Task> addUserToTask(@PathVariable Long task_id, @PathVariable Long user_id){
            if(taslApiservice.addUserToTaskByUserId(task_id,user_id)){
                return new ResponseEntity<>(taslApiservice.getTaskById(task_id), HttpStatus.OK);
            }else {
                return ResponseEntity.notFound().build();
            }

        }

}
