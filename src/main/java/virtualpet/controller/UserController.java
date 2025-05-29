package virtualpet.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import virtualpet.dto.LoginRequest;
import virtualpet.dto.RegisterRequest;
import virtualpet.model.Pet;
import virtualpet.model.User;
import virtualpet.services.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/data")
    public ResponseEntity<String> getUserData() {
        return ResponseEntity.ok("User data");
    }

    @GetMapping("/allUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> allUsers(){
        List<User> userList = userService.allUsers();
        return ResponseEntity.ok(userList);
    }

  /*  @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        User newUser = userService.register(request);
        return ResponseEntity.ok("User registered successfully: "  + newUser.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        User user = userService.login(request);
        return ResponseEntity.ok("Welcome back " + user.getUsername());
    } */
}
