package ayd.proyecto1.fastdelivery.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewCardDto {

    private String title;

    private Double shippingPrice;

    private Integer discount;

    private Integer cancellationPayment;

    private Integer freeCancellations;

    private Integer minPackages;

    private Integer maxPackages;
}
