package spring.home6.api;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.home6.exceptions.EntityNotFoundException;
import spring.home6.repositories.TaskRepository;
import spring.home6.repositories.UserRepository;
import spring.home6.task.Task;
import spring.home6.task.TaskDto;
import spring.home6.user.User;
import spring.home6.user.UserDto;

import java.util.List;

@Service
public class UserApiService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public UserApiService(UserRepository repository, TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = repository;
    }

    public UserDto createUser(UserDto userDto) {
        User user = convertEntity(userDto);
        return convertDto(user);
    }

    private @NotNull User convertEntity(@NotNull UserDto userDto) {
        User user = new User();
        if (userDto.getTaskId() != null) {
            user.setTask(taskRepository.getById(userDto.getTaskId()));
        }
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setId(userDto.getId());
        return user;
    }

    private @NotNull UserDto convertDto(@NotNull User user) {
        UserDto userDto = new UserDto();
        if (user.getTask() != null) {
            userDto.setTaskId(user.getTask().getId());
        }
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        return userDto;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public User updateUser(Long id, @NotNull User user) {
        User currentUser = getUserById(id);
        currentUser.setEmail(user.getEmail());
        currentUser.setName(user.getName());
        currentUser.setTask(user.getTask());
        userRepository.save(currentUser);
        return currentUser;
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


}
