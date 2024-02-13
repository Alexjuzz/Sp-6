package spring.home6.api;

import org.apache.catalina.loader.ResourceEntry;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.home6.exceptions.EntityNotFoundException;
import spring.home6.repositories.TaskRepository;
import spring.home6.task.Task;
import spring.home6.task.Status;
import spring.home6.task.TaskDto;
import spring.home6.user.User;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskApiService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserApiService userApiService;

    public TaskDto createTask(TaskDto taskDto) {

        Task task = convertToEntity(taskDto);
        task = taskRepository.save(task);
        return convertToDto(task);
    }

    //region convert DTO and Entity
    private @NotNull Task convertToEntity(@NotNull TaskDto taskDto) {
        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDate(LocalDate.now());
        task.setStatus(taskDto.getStatus());
        task.setDescription(taskDto.getDescription());
        if (taskDto.getUserList() != null) {
            for (Long userId : taskDto.getUserList()) {
                User user = userApiService.getUserById(userId);
                if (user != null) {
                    task.addUser(user);
                } else {
                    throw new EntityNotFoundException("User not found");
                }
            }
        }
        return task;
    }


    private @NotNull TaskDto convertToDto(@NotNull Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setName(task.getName());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        if (task.getUserList() != null && !task.getUserList().isEmpty()) {
            for (User u : task.getUserList()) {
                taskDto.addIdToList(u.getId());
            }
        }
        return taskDto;
    }
//endregion

    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found: " + id));
        return convertToDto(task);
    }


    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found: " + id));
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setDate(LocalDate.now());
        task.setStatus(taskDto.getStatus());
        taskRepository.save(task);
        return convertToDto(task);
    }

    public TaskDto addUser(Long task_id, Long user_id) {

        Task task = convertToEntity(getTaskById(task_id));
        task.addUser(userApiService.getUserById(user_id));
        taskRepository.save(task);
        return convertToDto(task);

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
}
