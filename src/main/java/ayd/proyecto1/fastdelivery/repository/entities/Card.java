package ayd.proyecto1.fastdelivery.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(name = "shippingPrice")
    private Double shippingPrice;

    private Integer discount;

    @Column(name = "cancellationPayment")
    private Integer cancellationPayment;

    @Column(name = "freeCancellations")
    private Integer freeCancellations;

    @Column(name = "minPackages")
    private Integer minPackages;

    @Column(name = "maxPackages")
    private Integer maxPackages;
/*
    @OneToMany(fetch = FetchType.LAZY)
    private List<BusinessCard> businessCards;*/
}
