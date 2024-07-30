package ch.roester.part;

import ch.roester.mapper.EntityMapper;
import ch.roester.product.Product;
import ch.roester.product.ProductMapper;
import ch.roester.product.ProductRequestDTO;
import ch.roester.product.ProductResponseDTO;
import ch.roester.property.PropertyMapper;
import ch.roester.tag.TagMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PartMapper extends EntityMapper<PartRequestDTO, PartResponseDTO, Part> {

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