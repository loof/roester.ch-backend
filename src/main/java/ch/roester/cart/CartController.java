package ch.roester.cart;

import ch.roester.app_user.AppUser;
import ch.roester.app_user.AppUserService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Set;


@RequestMapping(CartController.REQUEST_MAPPING)
@RestController
@Slf4j
public class CartController {

    public static final String REQUEST_MAPPING = "/carts";
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final AppUserService appUserService;

    @Autowired
    public CartController(CartService cartService, AppUserService appUserService, CartItemService cartItemService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.appUserService = appUserService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO> findById(@Parameter(description = "Id of cart to get") @PathVariable("id") Integer id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            AppUser appuser = appUserService.findByEmail(currentPrincipalName);
            if (!Objects.equals(appuser.getCart().getId(), id)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized");
            }
            CartResponseDTO cartResponseDTO = cartService.findById(id);
            return ResponseEntity.ok(cartResponseDTO);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
    }

    @PostMapping
    public ResponseEntity<CartResponseDTO> createCart(@RequestBody @Valid CartRequestDTO cartRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cartService.save(cartRequestDTO));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart could not be created");
        }
    }

    @PostMapping("{id}/items")
    public ResponseEntity<CartResponseDTO> createCartItems(
            @RequestBody @Valid CartItemRequestDTO[] cartItemRequestDTOS,
            @PathVariable("id") Integer cartId,
            @RequestParam(name = "add", defaultValue = "false") boolean add) {  // Get the 'add' query parameter

        try {
            // Call the service to save all cart items, passing the 'add' parameter
            Set<CartItemResponseDTO> savedCartItems = cartItemService.saveAll(cartId, cartItemRequestDTOS, add);

            if (!savedCartItems.isEmpty()) {
                CartResponseDTO cartResponseDTO = cartService.findById(cartId);
                return ResponseEntity.status(HttpStatus.CREATED).body(cartResponseDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart items could not be created");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart item could not be created");
        }
    }


    @PatchMapping("{cartId}/items/{itemId}")
    public ResponseEntity<CartItemRequestDTO> update(@Valid @RequestBody CartItemRequestDTO cartItemRequestDTO, @PathVariable("cartId") Integer cartId, @PathVariable("itemId") Integer itemId) {
        try {
            CartItemRequestDTO updatedCartItem = cartItemService.update(itemId, cartId, cartItemRequestDTO);
            return ResponseEntity.ok(updatedCartItem);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cart item could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found");
        }
    }


    @PatchMapping("{id}")
    public ResponseEntity<CartResponseDTO> update(@Valid @RequestBody CartRequestDTO cartRequestDTO, @PathVariable("id") Integer id) {
        try {
            CartResponseDTO updatedCart = cartService.update(id, cartRequestDTO);
            return ResponseEntity.ok(updatedCart);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cart could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@Parameter(description = "Id of cart to delete") @PathVariable("id") Integer id) {
        try {
            cartService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found");
        }
    }

    @DeleteMapping("/{id}/items")
    public ResponseEntity<Void> deleteCartItem(@Parameter(description = "Id of cart item to delete") @PathVariable("id") Integer id) {
        try {
            cartItemService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found");
        }
    }

}