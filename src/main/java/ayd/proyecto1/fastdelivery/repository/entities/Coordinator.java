package ayd.proyecto1.fastdelivery.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Coordinator")
public class Coordinator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_branch", referencedColumnName = "id")
    private Branch branch;

    @OneToMany(mappedBy = "coordinator", fetch = FetchType.LAZY)
    private List<ChatCoordinatorBusiness> chatCoordinatorBusinesses;

    @OneToMany(mappedBy = "coordinator", fetch = FetchType.LAZY)
    private List<ChatCoordinatorDeliveryPerson> chatCoordinatorDeliveryPeople;
}
