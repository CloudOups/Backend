package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.pi.controllers.EmailController;
import tn.esprit.pi.entities.*;
import tn.esprit.pi.repositories.CodePromoRepository;
import tn.esprit.pi.repositories.IReservationTerrRepository;
import tn.esprit.pi.repositories.ITerrainRepository;
import tn.esprit.pi.repositories.UserRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor

public class ReservationTerrServices implements IReservationTerrServices {
    private final CodePromoRepository codePromoRepository;
    private final  IReservationTerrRepository reservationTerrRepository;
    private final  ITerrainRepository terrainRepository;
    private final UserRepository userRepository;
    private final EmailController emailController;
    private final IQrEmailService iqrService;


    /* @Override
     public ReservationTerrain addReservationTerrain(ReservationTerrain reservationTerrain,Long idUser,Long idTerrain) {
         Terrain terrain =terrainRepository.findById(idTerrain).orElse(null);
         User user =userRepository.findById(idUser).orElse(null);
         if(user== null){
             log.info("user not found");return null;}
         else if(terrain== null){
             log.info("terrain not found");return null;}
 else {
             reservationTerrain.setTerrain(terrain);
             reservationTerrain.setUser(user);
             return reservationTerrRepository.save(reservationTerrain);
         }
     }*/
    @Override
    public ReservationTerrain addReservationTerrain(ReservationTerrain reservationTerrain, Integer idUser, Long idTerrain) {
        Terrain terrain = terrainRepository.findById(idTerrain).orElse(null);
        User user = userRepository.findById(idUser).orElse(null);

        if (user == null) {
            log.warn("User not found");
            return null;
        } else if (terrain == null) {
            log.warn("Terrain not found");
            return null;
        } else {
            // Check if the terrain is available at the time of the new reservation
            if (!isTerrainAvailable(terrain, reservationTerrain)) {
                log.warn("Terrain is already reserved at that time");
                return null;
            }
            CodePromo promoCode = codePromoRepository.findValidPromoCodesByUserId(idUser);
            //log.info(promoCode.getCode());
            generatePromoCodeForUserWithReservations(user);
            if(promoCode==null){
                double totalPrice = calculateReservationPrice(reservationTerrain.getDateDebut(), reservationTerrain.getDateFin());
                reservationTerrain.setPrixReser(totalPrice);}
    else { if( isPromoCodeValidAndActive(promoCode.getCode())){
            double totalPrice = calculateReservationPrice(reservationTerrain.getDateDebut(), reservationTerrain.getDateFin())*0.9;
            reservationTerrain.setPrixReser(totalPrice);
            promoCode.setEtatCodePromo(Boolean.FALSE);
        }}
            terrain.setStatusTerrain(StatusTerrain.valueOf("Reserve"));
                reservationTerrain.setTerrain(terrain);
                reservationTerrain.setUser(user);
            return reservationTerrRepository.save(reservationTerrain);
        }}
    public double calculateReservationPrice(LocalDateTime datedebut, LocalDateTime datefin) {
        // Obtenez la durée de la réservation en minutes
        // Obtenez la durée de la réservation en minutes
      long durationInMinutes = Duration.between(datedebut, datefin).toMinutes();
        // Calculer le prix en fonction de la durée de la réservation
        double pricePerMinute = 0.5; // Prix par minute (à titre d'exemple)
        double totalPrice = durationInMinutes * pricePerMinute;
        return totalPrice;
    }

    private boolean isTerrainAvailable(Terrain terrain, ReservationTerrain newReservation) {
        // Get all reservations for the terrain
        List<ReservationTerrain> reservations = reservationTerrRepository.findByTerrain(terrain);

        // Check if any existing reservation overlaps with the new reservation
        for (ReservationTerrain reservation : reservations) {
            if (reservation.getDateFin().isAfter(newReservation.getDateDebut()) &&
                    newReservation.getDateFin().isAfter(reservation.getDateDebut())) {
                // Les deux intervalles se chevauchent
                return false; // Terrain n'est pas disponible
            }

        }
        return true; // Terrain is available
    }

    @Override
    public ReservationTerrain updateReservationTerrain(ReservationTerrain reservationTerrain) {
        //    if(reservationTerrain.getEtatReser()==true &&)
        return reservationTerrRepository.save(reservationTerrain);
    }

    @Override
    public void delete(Long numRes) {
        reservationTerrRepository.deleteById(numRes);

    }

    @Override
    public ReservationTerrain getById(Long numRes) {
        return reservationTerrRepository.findById(numRes).get();
    }

    @Override
    public List<ReservationTerrain> findReservationByEtat(boolean etat) {
        List<ReservationTerrain> result = reservationTerrRepository.findReserByEtatReser(etat);
        return result;
    }


    @Override
    public List<ReservationTerrain> getAll() {
        return (List<ReservationTerrain>) reservationTerrRepository.findAll();
    }

    @Override
    public List<ReservationTerrain> getResByTypeRes(String typeRes) {
        try {
            TypeReservation type = TypeReservation.valueOf(typeRes);
            List<ReservationTerrain> result = reservationTerrRepository.findByTypeRes(type);
            return result;
        } catch (IllegalArgumentException e) {
            log.warn("Invalid type of reservation: " + typeRes);
            return null;
        }
    }

    @Override

    public List<ReservationTerrain> getResByTerrain(String nomTerrain) {
        Terrain terrain = terrainRepository.findByNomTerrain(nomTerrain);
        if (terrain != null) {
            List<ReservationTerrain> result = reservationTerrRepository.findByTerrain(terrain);
            return result;
        }
        return Collections.emptyList();
    }
    public List<ReservationTerrain> getResByUser(Integer userId) {
        List<ReservationTerrain> reservationsByUser = reservationTerrRepository.findByUser_Id(userId);
        if (reservationsByUser != null) {
            return reservationsByUser;
        }
        return Collections.emptyList();
    }

    @Override
    public Terrain getMostReservedTerrainByType(TypeTerrain typeTerrain) {
        List<Terrain> terrainsOfType = terrainRepository.findByTypeTerrain(typeTerrain);
        if (terrainsOfType.isEmpty()) {
            log.warn("No terrains of type {} found", typeTerrain);
            return null;
        }

        Optional<Terrain> mostReservedTerrain = terrainsOfType.stream()
                .max(Comparator.comparingInt(terrain -> reservationTerrRepository.findByTerrain(terrain).size()));
        return mostReservedTerrain.orElse(null);
    }
    @Override
    public Page<ReservationTerrain> getAllPagination(Pageable pageable){
        return  reservationTerrRepository.findAll(pageable);
    }
    @Override
    public Page<ReservationTerrain> testerBYUser(int page, int size) {
        Sort sort=Sort.by("user").ascending();
        PageRequest pageable = PageRequest.of(page, size,sort); // Page 1 avec 10 éléments par page, ajustez selon vos besoins
        return reservationTerrRepository.findAll( pageable);    }
    @Override
    public Page<ReservationTerrain> testerByDateDebut(int page, int size) {
        Sort sort=Sort.by("dateDebut").ascending();
        PageRequest pageable = PageRequest.of(page, size,sort); // Page 1 avec 10 éléments par page, ajustez selon vos besoins
        return reservationTerrRepository.findAll( pageable);    }
    @Override
    public Page<ReservationTerrain> testerByDateFin(int page, int size) {
        Sort sort=Sort.by("dateFin").ascending();
        PageRequest pageable = PageRequest.of(page, size,sort); // Page 1 avec 10 éléments par page, ajustez selon vos besoins
        return reservationTerrRepository.findAll( pageable);    }
    @Override
    public Page<ReservationTerrain> testerByType(int page, int size) {
        Sort sort=Sort.by("typeRes").ascending();
        PageRequest pageable = PageRequest.of(page, size,sort); // Page 1 avec 10 éléments par page, ajustez selon vos besoins
        return reservationTerrRepository.findAll( pageable);    }
    @Override
    public Page<ReservationTerrain> testerByNomTerrain(int page, int size) {
        Sort sort=Sort.by("terrain").ascending();
        PageRequest pageable = PageRequest.of(page, size,sort); // Page 1 avec 10 éléments par page, ajustez selon vos besoins
        return reservationTerrRepository.findAll( pageable);    }
    @Override
    public Page<ReservationTerrain> testerByStatus(int page, int size) {
        Sort sort=Sort.by("etatReser").ascending();
        PageRequest pageable = PageRequest.of(page, size,sort); // Page 1 avec 10 éléments par page, ajustez selon vos besoins
        return reservationTerrRepository.findAll( pageable);    }

    @Override
    @Scheduled(cron = "* * */24 * * *")
    public List<Terrain> getMostReservedTerrains() {
        List<Terrain> terrains = terrainRepository.findAll(); // Suppose que vous avez une méthode findAll() pour récupérer tous les terrains
        if (terrains.isEmpty()) {
            log.warn("Aucun terrain trouvé dans la base de données");
            return Collections.emptyList();
        }

        // Triez les terrains en fonction du nombre de réservations, puis prenez les trois premiers
        List<Terrain> mostReservedTerrains = terrains.stream()
                .sorted((t1, t2) -> Integer.compare(
                        reservationTerrRepository.findByTerrain(t2).size(),
                        reservationTerrRepository.findByTerrain(t1).size()))
                .limit(3)
                .collect(Collectors.toList());

        return mostReservedTerrains;
    }

    @Override
    public String generatePromoCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder promoCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            promoCode.append(characters.charAt(random.nextInt(characters.length())));
        }
        log.info(promoCode.toString());
        return promoCode.toString();    }
    @Override
    public CodePromo generatePromoCodeForUserWithReservations(User user) {
        Set<ReservationTerrain> userReservations = user.getReservationTerrains();
        Map<YearMonth, Integer> reservationsPerMonth = new HashMap<>();
        for (ReservationTerrain reservation : userReservations) {
            if (reservation != null && reservation.getDateRes() != null) {
                YearMonth reservationMonth = YearMonth.from(reservation.getDateRes());
                reservationsPerMonth.put(reservationMonth, reservationsPerMonth.getOrDefault(reservationMonth, 0) + 1);
            }
        }

        // Vérifiez si l'utilisateur a au moins 3 réservations dans un même mois
        for (Map.Entry<YearMonth, Integer> entry : reservationsPerMonth.entrySet()) {
            if (entry.getValue() >= 3) {
                YearMonth yearMonth = entry.getKey();
                List<CodePromo> existingPromoCodes = codePromoRepository.findPromoCodesByUserIdAndMonth(user.getId(), yearMonth.getYear(), yearMonth.getMonthValue());
                // Vérifiez si l'utilisateur a déjà un code promo non expiré et non utilisé pour ce mois
                if (!existingPromoCodes.isEmpty()) {
                    // Si un code promo valide existe déjà pour ce mois, retournez-le
                    return existingPromoCodes.get(0); // Assuming only one valid promo code per month is allowed
                } else {
                    // Générez un code promo unique pour ce mois
                    String promoCode = generatePromoCode(8);

                    // Définissez la date d'expiration du code promo (valable pendant 5 jours à partir de maintenant)
                    LocalDateTime expirationDate = LocalDateTime.now().plusDays(5);

                    // Créez un nouveau code promo
                    CodePromo newPromoCode = new CodePromo();
                    newPromoCode.setCode(promoCode);
                    newPromoCode.setDateExpCodePromo(expirationDate);
                    newPromoCode.setUser(user);
                    newPromoCode.setEtatCodePromo(true); // Le code promo est initialisé comme valable
                    codePromoRepository.save(newPromoCode);
                    // Get user's email address from the reservation or any other source
                    String userEmail = user.getEmail(); // Assuming user has an email field

                    String subject = "CODE PROMO";
                    String message = "Bonjour " + user.getFirstname() + ",\n\n"
                    +"Félicitations ! Vous avez reçu un nouveau code promo.\n"+
                    "Scannez votre Code et l'utiliser avant la  "+
                     "Date d'expiration: " + newPromoCode.getDateExpCodePromo().toString() + "\n\n"+
                     "Utilisez ce code lors de votre prochaine commande pour bénéficier de la réduction.\n"+
                     "Merci de votre fidélité !\n\n"+
                    "Cordialement,\nL'équipe de votre entreprise "
                            ;

                    // Send email
                    EmailRequest2 emailRequest = new EmailRequest2(userEmail, subject, message,newPromoCode.getCode());

                    ResponseEntity<?> emailResponse = emailController.sendEmailQr(emailRequest);

                    return newPromoCode;
                }
            }
        }
        log.warn("nooooo");
        return null; // Aucun code promo valide trouvé
    }


    // Supposez que vous ayez une méthode pour utiliser un code promo pour une réduction
 @Override
    public void applyPromoCodeForUser(CodePromo promoCode, User user) {
        if (promoCode.getDateExpCodePromo().isBefore(LocalDateTime.now())) {
            // Si la date d'expiration est passée, marquez le code promo comme expiré
            promoCode.setEtatCodePromo(false);
            codePromoRepository.save(promoCode);
            // Vous pouvez également informer l'utilisateur que le code promo est expiré
            System.out.println("Le code promo est expiré.");
        } else {
            // Appliquez la réduction pour l'utilisateur
            // Votre logique pour appliquer la réduction ici
            System.out.println("La réduction a été appliquée avec succès !");
        }
    }
    public boolean isPromoCodeValidAndActive(String promoCode) {
        // Recherche du code promo dans la base de données
        CodePromo codePromo = codePromoRepository.findByCodeAndEtatCodePromo(promoCode, true);

        // Vérifie si le code promo a été trouvé et est actif
        return codePromo != null; // Retourne true si le code promo est trouvé, sinon false
    }

}
