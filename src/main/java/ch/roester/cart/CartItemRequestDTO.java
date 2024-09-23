package ch.roester.cart;

import ch.roester.variant.VariantRequestDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class CartItemRequestDTO {
    private Integer id;
    private Integer eventId;
    private Integer variantId;
    private Double amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemRequestDTO that = (CartItemRequestDTO) o;
        return getVariantId().equals(that.getVariantId()) && getEventId() == that.getEventId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVariantId(), getEventId());
    }
}
