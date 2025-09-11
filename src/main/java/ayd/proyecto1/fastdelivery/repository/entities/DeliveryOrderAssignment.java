package ayd.proyecto1.fastdelivery.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "DeliveryOrderAssignment")
public class DeliveryOrderAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_delivery_order", referencedColumnName = "id")
    private DeliveryOrder deliveryOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_delivery_person", referencedColumnName = "id")
    private DeliveryPerson deliveryPerson;

    @OneToMany(mappedBy = "deliveryOrderAssignment", fetch = FetchType.LAZY)
    private List<ChatCoordinatorDeliveryPerson> chatCoordinatorDeliveryPeople;
}
