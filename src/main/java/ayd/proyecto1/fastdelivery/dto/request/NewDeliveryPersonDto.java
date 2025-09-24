package ayd.proyecto1.fastdelivery.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewDeliveryPersonDto {

    private Integer userId;

    private Integer contractId;

    private Integer branchId;

}
