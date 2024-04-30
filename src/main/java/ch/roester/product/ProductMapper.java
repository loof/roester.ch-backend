package ch.roester.product;

import ch.roester.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductRequestDTO, Product> {
}