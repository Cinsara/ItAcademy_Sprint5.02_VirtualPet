package virtualpet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import virtualpet.dto.LoginRequest;
import virtualpet.dto.LoginResponse;
import virtualpet.dto.RegisterRequest;
import virtualpet.model.CustomUserDetails;
import virtualpet.model.User;
import virtualpet.services.UserService;
import virtualpet.util.JwtUtil;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.validateUser(request);
            UserDetails userDetails = new CustomUserDetails(user);
            String jwtToken = jwtUtil.generateToken(userDetails);
            LoginResponse loginResponse = new LoginResponse((long) user.getId(), user.getEmail(), jwtToken);
            return ResponseEntity.ok(loginResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User newUser = userService.register(request);
            return ResponseEntity.ok("Successfully registered user: " + newUser.getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
