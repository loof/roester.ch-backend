package ch.roester.product;

import ch.roester.mapper.EntityMapper;
import ch.roester.part.Part;
import ch.roester.part.PartMapper;
import ch.roester.property.PropertyMapper;
import ch.roester.tag.Tag;
import ch.roester.tag.TagMapper;
import ch.roester.tag.TagResponseDTO;
import ch.roester.variant.VariantMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Mapper(componentModel = "spring", uses = {PropertyMapper.class, TagMapper.class, ProductMapper.class, PartMapper.class, VariantMapper.class})
public interface ProductMapper extends EntityMapper<ProductRequestDTO, ProductResponseDTO, Product> {

   /* @Override
    @Mapping(target = "madeOf", source = "madeOf", qualifiedByName = "partsToProducts")
    ProductResponseDTO toResponseDTO(Product product);*/

   /* @Named("partsToProducts")
    default Set<ProductResponseDTO> partsToProducts(Set<Part> parts) {
        Set<ProductResponseDTO> products = new HashSet<>();
        for (Part part : parts) {
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setId(part.getId());
            productResponseDTO.setName(part.getPart().getName());
            productResponseDTO.setDescription(part.getPart().getDescription());
            products.add(productResponseDTO);
        }
        return products;
    }*/
}