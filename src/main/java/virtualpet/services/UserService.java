package virtualpet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import virtualpet.dto.requests.LoginRequest;
import virtualpet.dto.response.LoginResponse;
import virtualpet.dto.requests.RegisterRequest;
import virtualpet.model.Pet;
import virtualpet.model.User;
import virtualpet.model.UserRol;
import virtualpet.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("This email is already in use");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRol(UserRol.USER);
        user.setWeight(request.getWeight());

        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }
        Pet pet = user.getPet();
        return new LoginResponse((long) user.getId(), user.getEmail(), user.getUsername(),pet);
    }

    public List<User> allUsers(){
        return userRepository.findAll();
    }

    public User userFound(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User validateUser(LoginRequest request) {

        System.out.println("🔐 Intentando login con: " + request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("✅ Usuario encontrado, comprobando contraseña");

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            System.out.println("❌ Contraseña incorrecta");

            throw new BadCredentialsException("Incorrect password");
        }

        System.out.println("🔓 Contraseña válida");

        return user;
    }
}
