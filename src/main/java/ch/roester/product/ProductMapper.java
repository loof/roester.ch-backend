package ch.roester.product;

import ch.roester.mapper.EntityMapper;
import ch.roester.property.PropertyMapper;
import ch.roester.tag.TagMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = "spring", uses = {PropertyMapper.class, TagMapper.class})
public interface ProductMapper extends EntityMapper<ProductRequestDTO, ProductResponseDTO, Product> {


}