package ayd.proyecto1.fastdelivery.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BranchDto {

    private Integer id;

    private String name;

    private String address;

    private String phone;
}
