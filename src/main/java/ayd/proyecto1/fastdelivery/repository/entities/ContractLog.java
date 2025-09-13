package ayd.proyecto1.fastdelivery.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "ContractLog")
public class ContractLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    private LocalTime time;

    private String action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contract", referencedColumnName = "id")
    private Contract contract;
}
