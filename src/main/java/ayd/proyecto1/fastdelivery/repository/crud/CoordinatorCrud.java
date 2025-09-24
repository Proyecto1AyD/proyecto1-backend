package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.repository.entities.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatorCrud extends JpaRepository<Coordinator, Integer> {

    @Query(value = "select * from coordinator where id_user = ? limit 1", nativeQuery = true)
    Coordinator getByIdUser(Integer id_user);
}
