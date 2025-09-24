package ayd.proyecto1.fastdelivery.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer commission;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer months;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contractType", referencedColumnName = "id")
    private ContractType contractType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status", referencedColumnName = "id")
    private ContractStatus status;
/*
    @OneToMany(mappedBy = "contract", fetch = FetchType.LAZY)
    private List<ContractLog> logs;*/
}
