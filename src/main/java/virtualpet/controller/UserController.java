package virtualpet.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import virtualpet.model.User;
import virtualpet.services.UserService;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/allUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> allUsers(){
        List<User> userList = userService.allUsers();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/diamonds")
    public ResponseEntity<Integer> getDiamonds(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.userFound(userDetails);
        return ResponseEntity.ok(user.getDiamonds());
    }
}
