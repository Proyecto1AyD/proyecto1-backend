package ayd.proyecto1.fastdelivery.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewCoordinatorEmployeeDto {


    private Integer userId;

    private Integer branchId; //id de sucursal

}
