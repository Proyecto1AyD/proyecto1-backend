package ayd.proyecto1.fastdelivery.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateCoordinatorDto {

    private Integer id;

    private Integer userId;

    private Integer branchId;


}
