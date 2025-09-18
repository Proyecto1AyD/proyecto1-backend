package ayd.proyecto1.fastdelivery.utils.enums;

import lombok.Getter;

@Getter
public enum RolEnum {

    COORDINADOR(1),
    REPARTIDOR(2);

    private Integer id;


    RolEnum(Integer id) {
        this.id = id;
    }
}
