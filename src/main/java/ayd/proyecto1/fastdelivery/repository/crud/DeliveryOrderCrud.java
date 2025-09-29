package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.repository.entities.BusinessCard;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryOrderCrud extends JpaRepository<DeliveryOrder, Integer> {
    @Query(value = "select * from delivery_order where id_business = ?", nativeQuery = true)
    List<DeliveryOrder> getDeliveriesOrdersByIdBusiness(Integer id_business);

    //reports
    @Query(value = "select * from delivery_order where id_delivery_order_status = ? ;", nativeQuery = true)
    Optional<List<DeliveryOrder>> getByStatus(Integer deliveryOrderStatusId);
}
