package tn.esprit.pi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long numequipe;
    String nomEquipe;
    int classement;
    int nbMemEquipe;
    @OneToOne(cascade = CascadeType.PERSIST )
    User chef;
    @ManyToOne
    Tournoi tournoi;
    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL)
    private Set<MembresEquipe> members;
    @JsonIgnore
    @OneToMany(mappedBy = "equipe")
    private Set<MembresEquipe> membresEquipe= new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "equipe_pending_members",
            joinColumns = @JoinColumn(name = "equipe_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> pendingMembers ;
}
