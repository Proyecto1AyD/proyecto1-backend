package ayd.proyecto1.fastdelivery.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
public class IncidentDto {

    private Integer id;

    private Integer deliveryOrderId;

    private Integer incidentTypeId;

    private String observations;

    private LocalDate date;

    private LocalTime time;

}
