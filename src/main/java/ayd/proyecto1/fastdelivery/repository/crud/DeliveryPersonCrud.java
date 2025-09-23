package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.repository.entities.BusinessCard;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryPersonCrud extends JpaRepository<DeliveryPerson, Integer> {

    @Query(value = "select * from delivery_person where id_branch = ?", nativeQuery = true)
    List<DeliveryPerson> getAllDeliveryPersonsByIdBranch(Integer id_branch);

    @Query(value = "select * from delivery_person where id_user = ? limit 1", nativeQuery = true)
    DeliveryPerson getByIdUser(Integer id_business);
}
