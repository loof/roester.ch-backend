package ch.roester.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class JwtResponseDTO {
    private final String accessToken;
    private final String email;

    public JwtResponseDTO(String accessToken, String email) {
        this.accessToken = accessToken;
        this.email = email;
    }



}
