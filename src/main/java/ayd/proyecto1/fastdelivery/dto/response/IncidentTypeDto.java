package ayd.proyecto1.fastdelivery.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
public class IncidentTypeDto {

    private Integer id;

    private String description;

    private Boolean isCostumer;
}
