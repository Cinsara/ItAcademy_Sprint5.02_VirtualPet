package virtualpet.dto.requests;

import lombok.Data;
import virtualpet.model.BodyType;
import virtualpet.model.UserRol;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private double weight;
    private BodyType bodyType;
}
