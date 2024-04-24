package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Equipe;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repositories.IEquipeRepository;
import tn.esprit.pi.repositories.IUserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class EquipeServices implements IEquipeServices{
IEquipeRepository equipeRepository;
IUserRepository userRepository;
    @Override
    public boolean isUserAlreadyInTeam(Long userId) {
        List<Equipe> allTeams =  (List<Equipe>)equipeRepository.findAll();

        for (Equipe equipe : allTeams) {
            Set<User> members = equipe.getMembresEquipe();

            for (User member : members) {
                if (member.getUserId().equals(userId)) {
                    return true;

                }
            }}
            return false;}


    @Override
    public Equipe addEquipe(Equipe equipe,Long idUser) {
        Equipe existingEquipeOptional = equipeRepository.findByNomEquipe(equipe.getNomEquipe());
        if (existingEquipeOptional!=null) {System.out.println("nom équipe existe!");return  null; }
        else{
        if (!isUserAlreadyInTeam(idUser)) {
            User user = userRepository.findById(idUser).orElse(null);
            equipe.setChefEquipe(user);
            Set<User> membresEquipe = new HashSet<>();
            membresEquipe.add(user);
            equipe.setMembresEquipe(membresEquipe);

            return equipeRepository.save(equipe);
        }
    else System.out.println("User already selected ");
    return null;
    }}

    @Override
    public Equipe updateEquipe(Equipe equipe) {
        return equipeRepository.save(equipe);
    }

    @Override
    public void delete(Long equipeID) {

        equipeRepository.deleteById(equipeID);
    }

    @Override
    public Equipe getById(Long equipeID) {
        return equipeRepository.findById(equipeID).get();
    }

    @Override
    public List<Equipe> getAll() {
        return (List<Equipe>)equipeRepository.findAll();
    }

    @Override
    public Equipe demandeAdhesion(Long idequipe, Long iduser) {
        User user = userRepository.findById(iduser).orElse(null);
       Equipe equipe= equipeRepository.findById(idequipe).orElse(null);
            if (equipe.getMembresEquipe().size() < equipe.getNbMemEquipe()) {
                if (!isUserAlreadyInTeam(iduser)) {
                    equipe.getMembresEnAttente().add(user);
        return equipeRepository.save(equipe);}
            else{
                    System.out.println("user already in team");
                }
            }
        else  {
            System.out.println("L'equipe est complete ");}

        return null;
    }

    @Override
    public Equipe traiterAdhesion(Long idequipe,Long userId, String reponse){
        Equipe equipe = equipeRepository.findById(idequipe).orElse(null);
        Set<User> pendingMembers = equipe.getMembresEnAttente();
        if (pendingMembers == null ||equipe.getChefEquipe() == null ) {
            System.out.println("errors");}
        for (User user : pendingMembers) {
            User selectedUser = userRepository.findById(user.getUserId()).orElse(null);
            if (equipe.getMembresEquipe().size() < equipe.getNbMemEquipe()) {
                if (selectedUser.getUserId() == userId && reponse.equals("accepted")) {
                    equipe.getMembresEquipe().add(user);
                    equipe.getMembresEnAttente().remove(selectedUser);
                    return equipeRepository.save(equipe);
                } else if (selectedUser.getUserId() == userId && reponse.equals("refused")) {
                    equipe.getMembresEnAttente().remove(selectedUser);
                    return equipeRepository.save(equipe);
                }
            } else {
                System.out.println("L'equipe est complete ");
            }
        }
        return null;
    }
   @Override
    public Equipe getByNom(String nomEquipe) {
       Equipe equipe= equipeRepository.findByNomEquipe(nomEquipe);
        if (equipe == null) {
           System.out.println("Pas d'équipe avec ce nom");
       }
       return equipe;
        }


}
