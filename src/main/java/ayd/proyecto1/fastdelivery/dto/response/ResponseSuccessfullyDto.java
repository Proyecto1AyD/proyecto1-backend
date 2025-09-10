package ayd.proyecto1.fastdelivery.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ResponseSuccessfullyDto {

    private HttpStatus code;

    private String message;

}
