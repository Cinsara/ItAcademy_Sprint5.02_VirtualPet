package virtualpet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import virtualpet.dto.LoginRequest;
import virtualpet.dto.RegisterRequest;
import virtualpet.model.User;
import virtualpet.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/data")
    public ResponseEntity<String> getUserData() {
        return ResponseEntity.ok("User data");
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
