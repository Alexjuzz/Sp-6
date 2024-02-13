package spring.home6.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.home6.repositories.UserRepository;
import spring.home6.user.User;

@Service
public class UserWebService {

    @Autowired
   private UserRepository userRepository;

   public UserWebService(UserRepository userRepository){
       this.userRepository = userRepository;
   }
    public User createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return userRepository.save(user);
   }
}
