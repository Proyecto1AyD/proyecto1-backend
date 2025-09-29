package ayd.proyecto1.fastdelivery.dto.internal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class NewReceiptDto {

    private Integer deliveryOrderAssignmentId;

    private Double amount;

    private Date date;
}
