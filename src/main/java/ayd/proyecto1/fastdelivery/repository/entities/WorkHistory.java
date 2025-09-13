package ayd.proyecto1.fastdelivery.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "WorkHistory")
public class WorkHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String month;

    private Double workedHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_delivery_person", referencedColumnName = "id")
    private DeliveryPerson deliveryPerson;

    @OneToMany(mappedBy = "workHistory", fetch = FetchType.LAZY)
    private List<WorkHistoryDetail> details;
}
