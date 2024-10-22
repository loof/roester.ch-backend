package ch.roester.app_user;


import ch.roester.location.LocationMapper;
import ch.roester.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = LocationMapper.class)
public interface AppUserMapper extends EntityMapper<AppUserRequestDTO, AppUserResponseDTO, AppUser> {

    @Override
    AppUserResponseDTO toResponseDTO(AppUser appUser);

}
