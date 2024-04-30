package ch.roester.security;


import ch.roester.app_user.AppUser;

public class AuthMapper {

    /**
     * Maps an AuthDTO object to a AppUser object.
     *
     * @param source the AuthDTO object to be mapped
     * @return the mapped AppUser object
     */
    public static AppUser fromRequestDTO(AuthRequestDTO source) {
        AppUser appUser = new AppUser();
        appUser.setEmail(source.getEmail());
        appUser.setPassword(source.getPassword());
        return appUser;
    }

    /**
     * Maps an AppUser object to an AuthDTO object.
     *
     * @param source the AppUser object to be mapped
     * @return the mapped AuthDTO object
     */
    public static AuthResponseDTO toResponseDTO(AppUser source) {
        return new AuthResponseDTO(source.getId(), source.getEmail());
    }
}
