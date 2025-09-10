package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.repository.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCrud extends JpaRepository<User, Integer> {


    @Query(value = "select * from usuario where username = ? ;", nativeQuery = true)
    Optional<User> getUserByUsername(String username);

}
