package ayd.proyecto1.fastdelivery.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Business_Card")
public class BusinessCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate affiliationDate;

    private Boolean loyaltyActive;

    private Boolean cancellations;

    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_business", referencedColumnName = "id")
    private User business;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_card", referencedColumnName = "id")
    private Card card;
}
