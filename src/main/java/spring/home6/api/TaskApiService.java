package spring.home6.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.home6.Exceptions.EntityNotFoundException;
import spring.home6.repositories.TaskRepository;
import spring.home6.task.Task;
import spring.home6.task.Status;
import spring.home6.user.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskApiService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserApiService userApiService;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found: "  + id));
    }

    public Task updateTask(Long id, Task task) {
        Task currentTask = getTaskById(id);
        currentTask.setDescription(task.getDescription());
        currentTask.setDate(task.getDate());
        currentTask.setName(task.getName());
        currentTask.setStatus(task.getStatus());
        taskRepository.save(currentTask);
        return currentTask;
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getTaskByStatus(Status status) {
        return getAllTask().stream().filter(task -> task.getStatus() == status).collect(Collectors.toList());
    }

    public List<Task> getTaskByStats(Status status) {
        return taskRepository.findByStatus(status);
    }

    public boolean addUserToTaskByUserId(Long taskId, Long userId) {
        Task currTask = getTaskById(taskId);
        User user = userApiService.getUserById(userId);
        if (currTask != null && user != null) {
            currTask.addUserToTask(user);
            taskRepository.save(currTask);
            return true;
        } else {
            return false;
        }
    }




}
