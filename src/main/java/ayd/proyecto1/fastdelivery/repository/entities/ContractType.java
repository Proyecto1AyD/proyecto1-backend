package ayd.proyecto1.fastdelivery.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "ContractType")
public class ContractType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String contractType;

    private LocalTime entryTime;

    private LocalTime exitTime;

    private Double baseSalary;

    @OneToMany(mappedBy = "contractType", fetch = FetchType.LAZY)
    private List<Contract> contracts;
}
