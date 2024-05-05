package ch.roester.security;

import ch.roester.app_user.AppUser;
import ch.roester.app_user.AppUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Collections.emptyList;

/**
 * Is used by AuthenticationManager for authenticate method
 */
@Service
public class RoesterUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    public RoesterUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> optAppUser = appUserRepository.findByEmail(email);

        if (optAppUser.isPresent()) {
            AppUser appUser = optAppUser.get();
            return new User(appUser.getEmail(), appUser.getPassword(), emptyList());
        } else {
            throw new UsernameNotFoundException(email);
        }
    }


}
