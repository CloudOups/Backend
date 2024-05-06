package tn.esprit.pi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements UserDetails,Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String firstname;
    private String lastname;
    @NonNull
    @Column(unique = true)
    private String email;
    private String password;
    String adresse;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String imageName;
    private String classe;
    private boolean enabled  ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date registrationDate = new Date() ;
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<ReservationTerrain> reservationTerrains;

    @OneToOne(mappedBy ="user")
    private Ticket ticket;
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy ="user")
    private Set<Commentaire> Commentaires;
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Publication> publications;

    @OneToOne(mappedBy = "user")
    private Panier panier;
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private  Set<Commande> commandes;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name())) ;
    }



    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled ;
    }


}
