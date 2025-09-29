package ayd.proyecto1.fastdelivery.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IncidentByBusinessIdReport4Dto {

    private Integer incidentId;

    private Integer deliveryOrderId;

    private String businessId;

    private String incidentTypeDescription;

    private String orderDeliveryDate;


}
