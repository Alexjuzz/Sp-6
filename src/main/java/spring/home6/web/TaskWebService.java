package spring.home6.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.home6.repositories.TaskRepository;
import spring.home6.task.Task;

import java.util.List;

@Service
public class TaskWebService {

    private final TaskRepository repository;

    public TaskWebService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task createTask(String name, String description) {
        Task currTask = new Task(name, description);
        repository.save(currTask);
        return currTask;
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public void deleteTask(Long id) {
        repository.deleteById(id);
    }

    public Task getTaskById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public void dropTables() {
        repository.deleteAll();
    }


    public Task updateTask(Long id,Task task) {
        Task currentTask = getTaskById(id);
        currentTask.setStatus(task.getStatus());
        currentTask.setName(task.getName());
        currentTask.setDescription(task.getDescription());
        repository.save(currentTask);
        return currentTask;
    }
    public void saveTask(Task task){
        repository.save(task);
    }
}
