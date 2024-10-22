package ch.roester.unit;

import ch.roester.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnitMapper extends EntityMapper<UnitRequestDTO, UnitResponseDTO, Unit> {

}