package ayd.proyecto1.fastdelivery.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(Include.NON_NULL)
public class UserInfoDto {

    private Integer userId;

    private String username;

    private String role;

    private Boolean autentication;


}
