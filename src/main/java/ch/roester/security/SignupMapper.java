package ch.roester.security;

import ch.roester.app_user.AppUser;
import ch.roester.location.LocationMapper;
import ch.roester.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface SignupMapper extends EntityMapper<SignupRequestDTO, SignupResponseDTO, AppUser> {

    @Override
    AppUser fromRequestDTO(SignupRequestDTO dto);

    @Override
    @Mapping(source = "password", target = "password", ignore = true)
    SignupResponseDTO toResponseDTO(AppUser appUser);
}
