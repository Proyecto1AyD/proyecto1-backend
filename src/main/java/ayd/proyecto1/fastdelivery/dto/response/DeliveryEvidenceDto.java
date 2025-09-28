package ayd.proyecto1.fastdelivery.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeliveryEvidenceDto {

    private Integer id;

    private Integer deliveryOrderId;

    private String photoUrl;

    private String observations;

}
