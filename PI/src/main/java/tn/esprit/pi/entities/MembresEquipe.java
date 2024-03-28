package tn.esprit.pi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Entity
    public class MembresEquipe implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        int id;
        @JsonIgnore

        @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;
        @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
