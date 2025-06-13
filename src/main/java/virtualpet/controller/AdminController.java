package virtualpet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import virtualpet.dto.PetDto;
import virtualpet.model.User;
import virtualpet.model.Pet;
import virtualpet.repositories.PetRepository;
import virtualpet.services.AdminService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final PetRepository petRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pets")
    public List<PetDto> getAllPets() {
        return petRepository.findAll().stream()
                .map(PetDto::from)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.ok("User deleted");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/pets/{petId}")
    public ResponseEntity<?> deletePet(@PathVariable Long petId) {
        adminService.deletePet(petId);
        return ResponseEntity.ok("Pet deleted");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/makeAdmin/{userId}")
    public ResponseEntity<?> makeAdmin(@PathVariable Long userId) {
        User updated = adminService.promoteUserToAdmin(userId);
        return ResponseEntity.ok(updated.getUsername() + " is an ADMIN now");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/user/{userId}/diamonds")
    public ResponseEntity<?> updateDiamonds(@PathVariable Long userId, @RequestParam int diamonds) {
        User updated = adminService.updateUserDiamonds(userId, diamonds);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/pet/{petId}/stats")
    public ResponseEntity<?> updatePetStats(@PathVariable Long petId, @RequestBody Pet updatedStats) {
        Pet updated = adminService.updatePetStats(petId, updatedStats);
        return ResponseEntity.ok(updated);
    }
}