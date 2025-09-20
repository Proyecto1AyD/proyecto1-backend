package ayd.proyecto1.fastdelivery.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeliveryPersonInfoDto {

    private Integer id;

    private Double wallet;

    private Integer contractId;

    private Integer userId;

    private Integer branchId;

    private Boolean available;

}
