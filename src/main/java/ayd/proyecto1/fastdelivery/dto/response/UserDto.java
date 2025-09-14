package ayd.proyecto1.fastdelivery.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {

    private Integer userId;

    private String nombre;

    private String email;

    private String username;

    private String password;

    private String telefono;

    private String direccion;

    private Integer rol;

}
