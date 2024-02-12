package spring.home6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.home6.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
