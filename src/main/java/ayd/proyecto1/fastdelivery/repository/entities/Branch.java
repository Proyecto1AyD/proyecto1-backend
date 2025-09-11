package ayd.proyecto1.fastdelivery.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Branch")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String address;

    private String phone;

    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private List<Coordinator> coordinators;

    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private List<DeliveryPerson> deliveryPeople;
}
