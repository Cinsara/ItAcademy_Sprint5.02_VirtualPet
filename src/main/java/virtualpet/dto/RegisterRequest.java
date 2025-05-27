package virtualpet.dto;

import lombok.Data;
import virtualpet.model.UserRol;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private UserRol userRol;
}
