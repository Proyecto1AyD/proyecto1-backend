package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.repository.entities.Coordinator;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryPersonCrud extends JpaRepository<DeliveryPerson, Integer> {
    @Query(value = "select * from delivery_person where id_user = ? limit 1", nativeQuery = true)
    DeliveryPerson getByIdUser(Integer id_user);

    @Query(value = "select * from delivery_person where id_contract = ? limit 1", nativeQuery = true)
    DeliveryPerson getByIdContract(Integer id_contract);
}
