package tn.esprit.pi.entities;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EmailRequest {
    private String to;
    private String subject;
    private String message;
}