package ayd.proyecto1.fastdelivery.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class NewBussinesCardDto {

    private Integer idBussines;

    private Integer idCard;

    private Boolean fidelizacionActiva = true;

    private Integer cancelaciones = 0;

    private Date affiliationDate = new Date();
}

