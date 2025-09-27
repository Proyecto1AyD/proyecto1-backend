package ayd.proyecto1.fastdelivery.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
public class DeliveryOrderLogDto {

    private Integer id;

    private Integer idDeliveryOrder;

    private LocalDate date;

    private LocalTime time;

    private String action;

}
