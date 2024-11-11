package ch.roester.shipping_method;

import ch.roester.carrier.CarrierResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingMethodResponseDTO extends ShippingMethodRequestDTO {
    private CarrierResponseDTO carrier;
}
