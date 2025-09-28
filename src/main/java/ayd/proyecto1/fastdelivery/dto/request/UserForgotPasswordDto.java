package ayd.proyecto1.fastdelivery.dto.request;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserForgotPasswordDto {

    private String username;

}
