package spring.home6.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.home6.repositories.UserRepository;
import spring.home6.user.User;

import java.util.List;

@Service
public class UserApiService {

    private final UserRepository userRepository;
    @Autowired
    public UserApiService(UserRepository repository){
        this.userRepository = repository;
    }
    public User createUser(User user){
        return userRepository.save(user);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
    }
    public User updateUser(Long id,User user){
        User currentUser = getUserById(id);
        currentUser.setEmail(user.getEmail());
        currentUser.setName(user.getName());
        currentUser.setTask(user.getTask());
        return currentUser;
    }
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }


}
