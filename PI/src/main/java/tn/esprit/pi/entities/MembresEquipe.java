package tn.esprit.pi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Entity
    public class MembresEquipe {
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
