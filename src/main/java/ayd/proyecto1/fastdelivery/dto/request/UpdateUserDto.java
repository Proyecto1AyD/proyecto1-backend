package ayd.proyecto1.fastdelivery.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateUserDto {
    private Integer userId;
    private String fieldName;
    private String newValue;
}
