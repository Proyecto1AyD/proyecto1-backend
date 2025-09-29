package ayd.proyecto1.fastdelivery.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
public class ReceiptInfoDto {

    private Integer receiptId;

    private Integer deliveryOrderAssignmentId;

    private Double amount;

    private LocalDate date;

}
