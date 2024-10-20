package ch.roester.app_user;


import ch.roester.event.Event;
import ch.roester.location.Location;
import ch.roester.location.LocationMapper;
import ch.roester.location.LocationRequestDTO;
import ch.roester.location.LocationResponseDTO;
import ch.roester.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = LocationMapper.class)
public interface AppUserMapper extends EntityMapper<AppUserRequestDTO, AppUserResponseDTO, AppUser> {

    @Override
    AppUserResponseDTO toResponseDTO(AppUser appUser);

}
