package ch.roester.product;

import ch.roester.mapper.EntityMapper;
import ch.roester.part.PartMapper;
import ch.roester.property.PropertyMapper;
import ch.roester.stock.Stock;
import ch.roester.tag.Tag;
import ch.roester.tag.TagMapper;
import ch.roester.unit.Unit;
import ch.roester.unit.UnitMapper;
import ch.roester.variant.VariantMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Mapper(componentModel = "spring", uses = {PropertyMapper.class, TagMapper.class, ProductMapper.class, PartMapper.class, VariantMapper.class, UnitMapper.class})
public interface ProductMapper extends EntityMapper<ProductRequestDTO, ProductResponseDTO, Product> {

    @Override
    @Mapping(source = "soldUnitId", target = "soldUnit", qualifiedByName = "soldUnitIdToSoldUnit")
    @Mapping(source = "stockId", target = "stock", qualifiedByName = "stockIdToStock")
    @Mapping(source = "tagIds", target = "tags", qualifiedByName = "tagIdsToTags")
    Product fromRequestDTO(ProductRequestDTO dto);

    @Named("tagIdsToTags")
    default Set<Tag> tagIdsToTags(Set<Integer> tagIds) {
        Set<Tag> tags = new HashSet<>();
        for (Integer tagId : tagIds) {
            Tag tag = new Tag();
            tag.setId(tagId);
            tags.add(tag);
        }
        return tags;
    }

    @Named("soldUnitIdToSoldUnit")
    default Unit soldUnitIdToSoldUnit(Integer unitId) {
        if (unitId != null) {
            Unit unit = new Unit();
            unit.setId(unitId);
            return unit;
        }
        return null;
    }

    @Named("stockIdToStock")
    default Stock stockIdToStock(Integer stockId) {
        if (stockId != null) {
            Stock stock = new Stock();
            stock.setId(stockId);
            return stock;
        }
        return null;
    }
}