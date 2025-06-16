package virtualpet.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import virtualpet.model.enums.BodyType;
import virtualpet.model.enums.UserRol;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String email;
    private String password;
    private double weight;

    @Enumerated(EnumType.STRING)
    private BodyType bodyType;

    @Enumerated(EnumType.STRING)
    private UserRol rol;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Pet pet;

    @Column(nullable = false)
    private int diamonds = 10;

    private double trainingTime;
    private LocalDateTime registerDate;

    @PrePersist
    public void prePersist(){
        this.registerDate = LocalDateTime.now();
    }
}
