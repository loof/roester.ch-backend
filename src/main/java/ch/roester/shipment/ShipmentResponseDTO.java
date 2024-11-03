package ch.roester.shipment;

import ch.roester.shipping_method.ShippingMethod;
import ch.roester.shipping_method.ShippingMethodResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShipmentResponseDTO extends ShipmentRequestDTO {
    private Integer id;
    private ShippingMethodResponseDTO shippingMethod;
}
