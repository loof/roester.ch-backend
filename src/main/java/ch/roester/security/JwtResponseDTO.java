package ch.roester.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class JwtResponseDTO {
    private final String accessToken;
    private final String email;
    private final Integer userId;
    private final Integer cartId;

    public JwtResponseDTO(String accessToken, String email, Integer userId, Integer cartId) {
        this.accessToken = accessToken;
        this.email = email;
        this.userId = userId;
        this.cartId = cartId;
    }
}
