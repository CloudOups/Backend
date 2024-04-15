package tn.esprit.pi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Terrain implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long numTerrain;
    String nomTerrain;
    @Enumerated(EnumType.STRING)
    StatusTerrain statusTerrain;
    @Enumerated(EnumType.STRING)
    TypeTerrain typeTerrain;
    @JsonIgnore
    @OneToMany(mappedBy = "terrain", cascade = CascadeType.ALL)
    Set<ReservationTerrain> reservations;
}
