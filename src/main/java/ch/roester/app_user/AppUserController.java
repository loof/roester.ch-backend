package ch.roester.app_user;

import ch.roester.location.LocationRequestDTO;
import ch.roester.location.LocationResponseDTO;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;


@RequestMapping(AppUserController.REQUEST_MAPPING)
@RestController
@Slf4j
public class AppUserController {

    public static final String REQUEST_MAPPING = "/users";
    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserResponseDTO> findById(@Parameter(description = "Id of app user to get") @PathVariable("id") Integer id) {
        try {
            AppUserResponseDTO appUser = appUserService.findById(id);
            return ResponseEntity.ok(appUser);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "App user not found");
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<AppUserRequestDTO> update(@Valid @RequestBody AppUserRequestDTO appUserRequestDTO, @PathVariable("id") Integer id) {
        try {
            AppUserResponseDTO updatedAppUser = appUserService.update(id, appUserRequestDTO);
            return ResponseEntity.ok(updatedAppUser);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "App user could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "App user not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Id of app user to delete") @PathVariable("id") Integer id) {
        try {
            appUserService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "App user not found");
        }
    }

}