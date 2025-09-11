package ayd.proyecto1.fastdelivery.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "WorkHistoryDetail")
public class WorkHistoryDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalTime time;

    private LocalDate date;

    private Boolean isStart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_work_history", referencedColumnName = "id")
    private WorkHistory workHistory;
}
