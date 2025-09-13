package ayd.proyecto1.fastdelivery.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "DeliveryOrder")
public class DeliveryOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String recipient;

    private String address;

    private LocalDate date;

    private LocalTime time;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_business", referencedColumnName = "id")
    private User business;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_delivery_order_status", referencedColumnName = "id")
    private DeliveryOrderStatus deliveryOrderStatus;

    @OneToMany(mappedBy = "deliveryOrder", fetch = FetchType.LAZY)
    private List<DeliveryOrderLog> logs;

    @OneToMany(mappedBy = "deliveryOrder", fetch = FetchType.LAZY)
    private List<DeliveryEvidence> evidences;

    @OneToMany(mappedBy = "deliveryOrder", fetch = FetchType.LAZY)
    private List<Incident> incidents;
}
