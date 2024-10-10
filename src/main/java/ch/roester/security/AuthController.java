package ch.roester.security;

import ch.roester.app_user.AppUser;
import ch.roester.app_user.AppUserService;
import ch.roester.cart.Cart;
import ch.roester.cart.CartRequestDTO;
import ch.roester.cart.CartResponseDTO;
import ch.roester.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(AuthController.PATH)
public class AuthController {
    public static final String PATH = "/auth";

    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AppUserService appUserService, AuthenticationManager authenticationManager, CartService cartService) {
        this.appUserService = appUserService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@Param("code") String code) {
        Map<String, Object> response = new HashMap<>();
        if (appUserService.verify(code)) {
            response.put("success", true);
        } else {
            response.put("success", false);
        }

        return ResponseEntity.ok(response);

    }

    @PostMapping("/signup")
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User was created successfully",
                    content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "User could not be created, username already in use",
                    content = @Content)
    })
    @SecurityRequirements //no security here, default is BEARER
    public ResponseEntity<?> signUp(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The user to register")
            @Valid @RequestBody SignupRequestDTO newAuthDTO
    ) {
        try {
            AppUser newAuth = AuthMapper.fromRequestDTO(newAuthDTO);
            AppUser savedAuth = appUserService.create(newAuth);
            return ResponseEntity.status(201).body(AuthMapper.toResponseDTO(savedAuth));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User could not be created, username already in use");
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User could not be created, there was a problem sending registration confirmation email");
        }
    }

    @PostMapping("/signin")
    @Operation(summary = "Receive a token for BEARER authorization")
    @SecurityRequirements //no security here, default is BEARER
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = JwtResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content)
    })
    public ResponseEntity<?> signIn(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The user to login")
            @RequestBody AuthRequestDTO newAuthDTO
    ) {
        String email = newAuthDTO.getEmail();
        String password = newAuthDTO.getPassword();
        Authentication token = new UsernamePasswordAuthenticationToken(email, password);

        if (authenticationManager.authenticate(token).isAuthenticated()) {
            AppUser user = appUserService.findByEmail(email);

            String jwt = JwtGenerator.generateJwtToken(email);
            return ResponseEntity.ok(new JwtResponseDTO(jwt, email, user.getId(), user.getCart().getId()));
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials!");
        }
    }

}
