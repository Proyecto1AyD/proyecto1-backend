package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.dto.response.ComissionDto;
import ayd.proyecto1.fastdelivery.repository.entities.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiptCrud extends JpaRepository<Receipt, Integer> {

    @Query(value = "select r.id, r.id_delivery_order_assignment, r.amount, r.`date` from receipt r inner join delivery_order_assignment doa on r.id_delivery_order_assignment  = doa.id where doa.id_delivery_person = ?;", nativeQuery = true)
    Optional<List<Receipt>> getByDeliveryPersonId(Integer deliveryPersonId);

    @Query("""
        SELECT ayd.proyecto1.fastdelivery.dto.response.ComissionDto(
            r.amount,
            doa.deliveryPersonId,
            r.date
        )
        FROM Receipt r
        INNER JOIN r.deliveryOrderAssignment doa
        WHERE doa.deliveryPersonId = :repartidorId
          AND r.date BETWEEN :fechaInicio AND :fechaFin
    """)
    Optional<List<ComissionDto>> getByAssignmentIdAndDate(Integer deliveryOrderAssignmentId, Date initDate, Date finishDate);


}
