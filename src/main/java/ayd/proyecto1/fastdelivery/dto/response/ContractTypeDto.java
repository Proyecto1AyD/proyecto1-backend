package ayd.proyecto1.fastdelivery.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
public class ContractTypeDto {

    private Integer id;

    private String contractType;

    private LocalTime entryTime;

    private LocalTime exitTime;

    private Double baseSalary;
}
