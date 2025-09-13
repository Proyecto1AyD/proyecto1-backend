package ayd.proyecto1.fastdelivery.repository.entities;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.beans.FeatureDescriptor;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Table(name = "validation_code")
@Entity
public class ValidationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    @Column(name = "expiration_time")
    private Date expirationTime;

    @Column(name = "is_used")
    private Boolean isUsed;

    private Integer attempts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

}
