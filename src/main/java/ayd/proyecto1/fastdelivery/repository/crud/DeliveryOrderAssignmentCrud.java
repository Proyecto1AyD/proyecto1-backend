package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.repository.entities.DeliveryEvidence;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryOrder;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryOrderAssignment;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryOrderAssignmentCrud extends JpaRepository<DeliveryOrderAssignment, Integer> {
    @Query(value = "select * from delivery_order_assignment where id_delivery_order = ? ", nativeQuery = true)
    List<DeliveryOrderAssignment>  getDeliveryOrderAssignmentByIdDeliveryOrder(Integer id_delivery_order);

    @Query(value = "select * from delivery_order_assignment where id_delivery_person = ? ", nativeQuery = true)
    List<DeliveryOrderAssignment>  getDeliveryOrderAssignmentByIdDeliveryPerson(Integer id_delivery_person);

    @Query(value = "select * from delivery_order_assignment where id_delivery_order = ? and id_delivery_person = ? LIMIT 1", nativeQuery = true)
    DeliveryOrderAssignment getDeliveryOrderAssignmentByIdDeliveryOrderIdDeliveryPerson(Integer id_delivery_order, Integer id_delivery_person);

    @Query(value = "select * from delivery_order_assignment where id_delivery_order = ? and active = ? LIMIT 1", nativeQuery = true)
    DeliveryOrderAssignment getDeliveryOrderAssignmentByIdDeliveryOrderActive(Integer id_delivery_order, Boolean active);

    @Query(value = "SELECT dp.*\n" +
            "FROM delivery_person dp\n" +
            "INNER JOIN user u ON dp.id_user = u.id\n" +
            "INNER JOIN contract c ON dp.id_contract = c.id\n" +
            "LEFT JOIN delivery_order_assignment doa \n" +
            "       ON doa.id_delivery_person = dp.id \n" +
            "WHERE c.id_status = 1 \n" +
            "  AND dp.available = TRUE\n" +
            "GROUP BY dp.id\n" +
            "ORDER BY COUNT(doa.id) ASC;", nativeQuery = true)
    List<DeliveryPerson> getDeliveryPersonByPriority();

}
