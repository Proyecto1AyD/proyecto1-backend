package ayd.proyecto1.fastdelivery.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ContractDto {

    private Integer id;

    private Integer commission;

    private Integer idContractType;

    private Integer months;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer idStatus;
}
