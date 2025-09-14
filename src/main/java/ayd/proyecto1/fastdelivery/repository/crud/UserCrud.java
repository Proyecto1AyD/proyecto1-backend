package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.repository.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCrud extends JpaRepository<User, Integer> {


    @Query(value = "select * from user where username = ? ;", nativeQuery = true)
    Optional<User> getUserByUsername(String username);

    @Query(value = "select * from user where id_role = ? ;", nativeQuery = true)
    List<User> getUserByIdRole(Integer id_role);

}
