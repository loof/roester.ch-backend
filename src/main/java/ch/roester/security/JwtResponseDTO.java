package ch.roester.security;


public final class JwtResponseDTO {
    private final String accessToken;
    private final String email;

    public JwtResponseDTO(String accessToken, String email) {
        this.accessToken = accessToken;
        this.email = email;
    }

    @Override
    public String toString() {
        return "JwtResponseDTO[" +
                "accessToken=" + accessToken + ", " +
                "email=" + email + ']';
    }

    public String getAccessToken() {
        return accessToken;
    }
}
