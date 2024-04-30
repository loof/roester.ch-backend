package ch.roester.app_user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer>, JpaSpecificationExecutor<AppUser> {
    Optional<AppUser> findByEmail(String email);

    @Query("SELECT u FROM AppUser u WHERE u.verificationCode = ?1")
    public AppUser findByVerificationCode(String code);
}