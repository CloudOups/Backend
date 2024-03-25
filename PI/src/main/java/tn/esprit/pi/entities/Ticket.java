package tn.esprit.pi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int IdTicket ;
    LocalDate dateTicket;
    @OneToOne(mappedBy ="ticket")
    User user;
    @ManyToOne
    Event event;

}
