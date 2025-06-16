package virtualpet.dto.requests;

import lombok.Data;

@Data
public class PetRequest {
    private String petName;
    private String type;
}
