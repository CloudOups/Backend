package tn.esprit.pi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Terrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int IdTerrain;
    String nomTerrain;
    @Enumerated(EnumType.STRING)
    StatusTerrain statusTerrain;
    @Enumerated(EnumType.STRING)
    TypeTerrain typeTerrain;
    @OneToMany(mappedBy = "terrain", cascade = CascadeType.ALL)
    Set<ReservationTerrain> reservations;

}
