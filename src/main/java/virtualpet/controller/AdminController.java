package virtualpet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import virtualpet.dto.LoginRequest;
import virtualpet.dto.RegisterRequest;
import virtualpet.model.User;
import virtualpet.model.UserRol;
import virtualpet.services.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/data")
    public ResponseEntity<String> getAdminData() {
        return ResponseEntity.ok("Administrator data");
    }

    /*
    @PostMapping("/admin/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createAdmin(@RequestBody RegisterRequest request) {
        request.setUserRol(UserRol.ADMIN);
        User admin = userService.register(request);
        return ResponseEntity.ok("Admin created successfully: " + admin.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        User user = userService.login(request);
        return ResponseEntity.ok("Welcome back " + user.getUsername());
    } */

}
