package ayd.proyecto1.fastdelivery.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
public class BussinesCardDto {

    private Integer id;

    private Integer idBussines;

    private Integer idCard;

    private Boolean loyaltyActive;

    private Integer cancellations;

    private LocalDate affiliationDate;

    private Boolean active;

}

