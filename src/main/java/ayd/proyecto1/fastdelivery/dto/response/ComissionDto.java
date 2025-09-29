package ayd.proyecto1.fastdelivery.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class ComissionDto {

    private String amount;

    private String deliveryPersonId;

    private Date date;
}
