package ayd.proyecto1.fastdelivery.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfoDto {

    private Integer userId;

    private String username;

    private String role;

    private Boolean autentication;


}
