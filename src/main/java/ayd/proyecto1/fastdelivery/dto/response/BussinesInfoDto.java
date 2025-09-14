package ayd.proyecto1.fastdelivery.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BussinesInfoDto {

    private Integer userId;

    private String name;

    private String username;

    private String role;

    private String email;

    private String phone;

    private String address;

}
