package ayd.proyecto1.fastdelivery.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "ChatCoordinatorDeliveryPerson")
public class ChatCoordinatorDeliveryPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String message;

    private LocalDate date;

    private LocalTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_coordinator", referencedColumnName = "id")
    private Coordinator coordinator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_delivery_order_assignment", referencedColumnName = "id")
    private DeliveryOrderAssignment deliveryOrderAssignment;
}
