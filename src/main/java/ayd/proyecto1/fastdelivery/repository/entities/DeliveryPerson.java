package ayd.proyecto1.fastdelivery.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "DeliveryPerson")
public class DeliveryPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(precision = 8, scale = 2) 
    private BigDecimal wallet;

    private Integer id_contract;

    private Integer id_branch;

    private Boolean available;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "deliveryPerson", fetch = FetchType.LAZY)
    private List<DeliveryOrderAssignment> deliveryOrderAssignments;

    @OneToMany(mappedBy = "deliveryPerson", fetch = FetchType.LAZY)
    private List<WorkHistory> workHistories;
}
