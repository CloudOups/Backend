package tn.esprit.pi.controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pi.entities.User;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stripe")
public class StripeApiController {

    // @PostMapping("/create-user")
    //public User createUser(@RequestBody User data) {
    // Logique pour créer un utilisateur avec Stripe
    // return data;

    //@RequestMapping("/createUser")
    //public User index (@RequestBody User data){
       // Map<String, Object> params = new HashMap<>();
        //params.put("description", "My first customer (created for api docs)");
        //User user = User.create(params);
        //return data;


    //@RequestMapping("/createUser")
   // public User index(@RequestBody Map<String, Object> userData) throws StripeException {
       // Stripe.apiKey = "votre_clé_secrète_stripe";

       // User customer = User.create(userData);
       // return customer;
   // }

    ///@Value("${stripe.apikey}")
   // String stripeKey;

    //@PostMapping("/create-user")
    //public User createCustomer(@RequestBody User data) throws StripeException {
       // Stripe.apiKey = stripeKey;


        //Map<String, Object> params = new HashMap<>();
        //params.put("name", data.getNom());
       // params.put("email", data.getEmail());

       // User user = User.create(params);

       // data.setUserId(User.getUserId());
       // return data;
   // }
}

