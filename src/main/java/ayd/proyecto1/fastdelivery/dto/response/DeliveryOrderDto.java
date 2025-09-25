package ayd.proyecto1.fastdelivery.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
public class DeliveryOrderDto {

    private Integer id;

    private Integer idBusiness;

    private String recipient;

    private String address;

    private LocalDate date;

    private LocalTime time;

    private Integer idDeliveryOrderStatus;

    private String description;
}
