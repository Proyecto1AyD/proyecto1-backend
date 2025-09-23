package ayd.proyecto1.fastdelivery.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
public class NewContractDto {

    private Integer commission;

    private LocalDate startDate;

    private LocalDate finalDate;

    private Integer idContractType;

    private Integer idStatus;

    private Integer months;
}
