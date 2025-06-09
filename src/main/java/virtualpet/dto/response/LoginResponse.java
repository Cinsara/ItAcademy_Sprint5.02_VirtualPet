package virtualpet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import virtualpet.model.Pet;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String email;
    private String token;
    private Pet pet;
}
