package ayd.proyecto1.fastdelivery.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
public class NewIncidentDto {

    private Integer deliveryOrderId;

    private Integer incidentTypeId;

    private String observations;

}
