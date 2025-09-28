package ayd.proyecto1.fastdelivery.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeliveryOrderAssignmentDto {

    private Integer id;

    private Integer idDeliveryOrder;

    private Integer idDeliveryPerson;

    private Boolean active;

}
