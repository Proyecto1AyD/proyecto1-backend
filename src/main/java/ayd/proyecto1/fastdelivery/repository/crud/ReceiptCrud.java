package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.repository.entities.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiptCrud extends JpaRepository<Receipt, Integer> {

    @Query(value = "select r.id, r.id_delivery_order_assignment, r.amount, r.`date` from receipt r inner join delivery_order_assignment doa on r.id_delivery_order_assignment  = doa.id where doa.id_delivery_person = ?;", nativeQuery = true)
    Optional<List<Receipt>> getByDeliveryPersonId(Integer deliveryPersonId);

    @Query(value = "select * from receipt r where id_delivery_order_assignment = ? ;",nativeQuery = true)
    Optional<List<Receipt>> getByAssignmentId(Integer deliveryOrderAssignmentId);
}
