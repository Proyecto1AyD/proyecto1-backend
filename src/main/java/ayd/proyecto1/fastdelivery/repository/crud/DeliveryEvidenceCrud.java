package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.repository.entities.DeliveryEvidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryEvidenceCrud extends JpaRepository<DeliveryEvidence, Integer> {
    @Query(value = "select * from delivery_evidence where id_delivery_order = ? ", nativeQuery = true)
    List<DeliveryEvidence> getDeliveryEvidenceByIdDeliveryOrder(Integer id_delivery_order);
}
