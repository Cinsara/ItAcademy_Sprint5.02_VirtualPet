package virtualpet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private UserRol rol;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    private Pet pet;

    private double trainingTime;
    private LocalDateTime registerDate;

    @PrePersist
    public void prePersist(){
        this.registerDate = LocalDateTime.now();
    }
}
