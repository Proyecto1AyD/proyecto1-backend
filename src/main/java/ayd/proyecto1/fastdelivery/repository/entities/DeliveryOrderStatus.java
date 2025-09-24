package ayd.proyecto1.fastdelivery.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "DeliveryOrderStatus")
public class DeliveryOrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String status;
/*
    @OneToMany(mappedBy = "deliveryOrderStatus", fetch = FetchType.LAZY)
    private List<DeliveryOrder> deliveryOrders;*/
}
