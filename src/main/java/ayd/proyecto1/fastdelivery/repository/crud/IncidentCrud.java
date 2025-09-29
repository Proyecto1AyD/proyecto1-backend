package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.repository.entities.DeliveryOrder;
import ayd.proyecto1.fastdelivery.repository.entities.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncidentCrud extends JpaRepository<Incident, Integer> {
    @Query(value = "select * from incident where id_delivery_order = ?", nativeQuery = true)
    List<Incident> getIncidentByIdDeliveryOrder(Integer id_delivery_order);


    @Query(value = """
            select i.id, i.id_delivery_order, do.id_business, it.description, do.`date` from incident i\s
            inner join delivery_order do on
            do.id = i.id_delivery_order\s
            inner join incident_type it on
            i.id_incident_type = it.id\s
            where do.id_business = ? ;
            """, nativeQuery = true)
    Optional<List<Object[]>> getIncidentsByBusinessId(Integer businessId);
}
