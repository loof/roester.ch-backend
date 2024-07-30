package ch.roester.unit;

import ch.roester.mapper.EntityMapper;
import ch.roester.product.Product;
import ch.roester.tag.Tag;
import ch.roester.tag.TagResponseDTO;
import ch.roester.variant.Variant;
import ch.roester.variant.VariantRequestDTO;
import ch.roester.variant.VariantResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UnitMapper extends EntityMapper<UnitRequestDTO, UnitResponseDTO, Unit> {


}