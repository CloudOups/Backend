package tn.esprit.pi.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import tn.esprit.pi.entities.User;

import java.security.Key;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    public boolean isTokenValid(String token, UserDetails userDetails) ;
    public boolean isTokenExpired(String token) ;

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) ;

    public String extractUsername(String token) ;

    public String generateRefreshToken(UserDetails userDetails,String role) ;

    public String generateToken(UserDetails userDetails,String role) ;

    public String generateToken(   Map<String, Object> extraClaims,
                                   UserDetails userDetails
                                    ,String role) ;


    public String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration,String role
    );

    public Claims extractAllClaims(String token) ;

    public Key getSignInkey() ;

}
