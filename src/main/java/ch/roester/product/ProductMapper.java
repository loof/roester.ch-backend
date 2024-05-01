package ch.roester.product;

import ch.roester.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductRequestDTO, Product> {
}