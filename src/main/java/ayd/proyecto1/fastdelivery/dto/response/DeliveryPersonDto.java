package ayd.proyecto1.fastdelivery.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeliveryPersonDto {

    private Integer id;

    private Integer userId;

    private Double wallet;

    private Integer contractId;

    private Integer branchId;

    private Boolean available;

}
