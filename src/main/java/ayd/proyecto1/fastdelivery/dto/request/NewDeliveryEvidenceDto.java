package ayd.proyecto1.fastdelivery.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewDeliveryEvidenceDto {

    private Integer deliveryOrderId;

    private String photoUrl;

    private String observations;

}
