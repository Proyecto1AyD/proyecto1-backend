package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.repository.entities.DeliveryOrder;
import ayd.proyecto1.fastdelivery.repository.entities.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentCrud extends JpaRepository<Incident, Integer> {
    @Query(value = "select * from incident where id_delivery_order = ?", nativeQuery = true)
    List<Incident> getIncidentByIdDeliveryOrder(Integer id_delivery_order);
}
