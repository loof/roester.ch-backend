package ch.roester.security;

import lombok.Getter;

import java.util.Objects;

@Getter
public class AuthResponseDTO {
    private final Integer id;
    private final String email;

    public AuthResponseDTO(Integer id, String username) {
        this.id = id;
        this.email = username;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AuthResponseDTO) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "AuthResponseDTO[" +
                "id=" + id + ", " +
                "username=" + email + ']';
    }

}
