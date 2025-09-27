package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.repository.entities.DeliveryOrder;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryOrderAssignment;
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
}
