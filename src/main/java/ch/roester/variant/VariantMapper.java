package ch.roester.variant;

import ch.roester.event.Event;
import ch.roester.event.EventResponseDTO;
import ch.roester.event_product_amount.EventProductAmount;
import ch.roester.mapper.EntityMapper;
import ch.roester.product.Product;
import ch.roester.tag.Tag;
import ch.roester.tag.TagRequestDTO;
import ch.roester.tag.TagResponseDTO;
import ch.roester.unit.UnitMapper;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring", uses = UnitMapper.class)
public interface VariantMapper extends EntityMapper<VariantRequestDTO, VariantResponseDTO, Variant> {

    @Override
    @Mapping(target = "productId", source = "product", qualifiedByName = "productToProductId")
    @Mapping(target = "price", ignore = true)
    VariantResponseDTO toResponseDTO(Variant variant);

    @Named("productToProductId")
    default Integer productToProductId(Product product) {
        return product.getId();
    }

    @AfterMapping // or @BeforeMapping
    default void calculatePrice(Variant variant, @MappingTarget VariantResponseDTO dto) {
        Double price = 0d;
        if (variant.getPrice() != null) {
            dto.setPrice(variant.getPrice());
        } else {
            dto.setPrice(variant.getProduct().getPricePerUnit().multiply(variant.getStockMultiplier()));
        }
    }

}