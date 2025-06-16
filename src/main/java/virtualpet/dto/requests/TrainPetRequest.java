package virtualpet.dto.requests;

import lombok.Data;
import virtualpet.model.enums.TrainingType;

@Data
public class TrainPetRequest {
    private TrainingType type;
    private int durationInSeconds;
}
