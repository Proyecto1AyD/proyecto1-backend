package ayd.proyecto1.fastdelivery.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeliveryOrderStatusDto {

    private Integer id;

    private String status;
}
