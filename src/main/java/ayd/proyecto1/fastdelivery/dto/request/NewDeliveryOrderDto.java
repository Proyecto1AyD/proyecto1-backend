package ayd.proyecto1.fastdelivery.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
public class NewDeliveryOrderDto {

    private Integer idBusiness;

    private String recipient;

    private String address;

    private LocalDate date;

    private LocalTime time;

    private String description;

}
