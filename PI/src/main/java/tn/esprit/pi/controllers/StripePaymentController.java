package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pi.entities.User;

@RequestMapping("/api")
@RestController
public class StripePaymentController {
    @Value("${stripe.apikey}")
    String stripeKey;

}
