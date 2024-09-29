package ch.roester.part;

import ch.roester.mapper.EntityMapper;
import ch.roester.product.Product;
import ch.roester.unit.Unit;
import ch.roester.unit.UnitMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {UnitMapper.class})
public interface PartMapper extends EntityMapper<PartRequestDTO, PartResponseDTO, Part> {

    @Override
    @Mapping(source = "partId", target = "part", qualifiedByName = "partIdToProduct")
    @Mapping(source = "productId", target = "product", qualifiedByName = "productIdToProduct")
    @Mapping(source = "unitId", target = "unit", qualifiedByName = "unitIdToUnit")
    Part fromRequestDTO(PartRequestDTO dto);

    @Named("unitIdToUnit")
    default Unit unitIdToUnit(Integer unitId) {
        if (unitId != null) {
            Unit unit = new Unit();
            unit.setId(unitId);
            return unit;
        }
        return null;
    }

    @Named("partIdToProduct")
    default Product partIdToProduct(Integer partId) {
        if (partId != null) {
            Product product = new Product();
            product.setId(partId);
            return product;
        }
        return null;
    }

    @Named("productIdToProduct")
    default Product productIdToProduct(Integer productId) {
        if (productId != null) {
            Product product = new Product();
            product.setId(productId);
            return product;
        }
        return null;
    }
}
