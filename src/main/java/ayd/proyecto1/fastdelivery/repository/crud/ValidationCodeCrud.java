package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.repository.entities.ValidationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidationCodeCrud extends JpaRepository<ValidationCode, Integer> {


    @Query(value = "select * from validation_code where user_id = ? ;", nativeQuery = true)
    Optional<ValidationCode> getByUser(Integer userId);


}
