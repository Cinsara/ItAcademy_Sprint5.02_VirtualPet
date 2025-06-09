package virtualpet.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import virtualpet.model.User;
import virtualpet.model.UserRol;
import virtualpet.repositories.UserRepository;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/makeAdmin/{userId}")
    public ResponseEntity<?> makeAdmin(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRol(UserRol.ADMIN);
        userRepository.save(user);
        return ResponseEntity.ok("USER promoted to ADMIN");
    }

}
