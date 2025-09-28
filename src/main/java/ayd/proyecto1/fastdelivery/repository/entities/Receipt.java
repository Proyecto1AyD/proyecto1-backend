package ayd.proyecto1.fastdelivery.repository.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "receipt")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_delivery_order_assignment", referencedColumnName = "id")
    private DeliveryOrderAssignment deliveryOrderAssignment;

    private Double amount;

    private Date date;

}
