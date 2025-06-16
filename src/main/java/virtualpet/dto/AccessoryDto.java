package virtualpet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessoryDto {
    private int id;
    private String name;
    private String imageUrl;
}
