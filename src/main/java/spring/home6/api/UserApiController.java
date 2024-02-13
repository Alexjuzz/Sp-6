package spring.home6.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.home6.user.User;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserApiController {

    private final UserApiService service;

    @Autowired
    public UserApiController(UserApiService service){
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public User getUserById(@PathVariable Long id){
        return service.getUserById(id);
    }

    @PostMapping(value = "/")
    public User createUser(@RequestBody User user){
        return service.createUser(user);
    }
    @GetMapping(value = "/all")
    public List<User> getAllUsers(){
        return service.getAllUsers();
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable Long id){
        service.deleteUserById(id);
    }
    @PutMapping(value = "/{id}")
    public User updateUser(@PathVariable Long id,@RequestBody User user){
        return service.updateUser(id,user);
    }


}
