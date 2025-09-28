package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.repository.entities.ContractLog;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryOrderLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryOrderLogCrud extends JpaRepository<DeliveryOrderLog, Integer> {
    @Query(value = "select * from delivery_order_log where id_delivery_order = ?", nativeQuery = true)
    List<DeliveryOrderLog> getDeliveryOrderLogsByIdDeliveryOrder(Integer id_delivery_order);
}
