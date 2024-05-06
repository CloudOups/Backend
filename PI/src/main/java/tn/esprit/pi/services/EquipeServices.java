package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Equipe;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repositories.IEquipeRepository;
import tn.esprit.pi.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class EquipeServices implements IEquipeServices{
    IEquipeRepository equipeRepository;
    UserRepository userRepository;
    @Override
    public boolean isUserAlreadyInTeam(Integer userId) {
        List<Equipe> allTeams =  (List<Equipe>)equipeRepository.findAll();

        for (Equipe equipe : allTeams) {
            Set<User> members = equipe.getMembresEquipe();

            for (User member : members) {
                if (member.getId().equals(userId)) {
                    return true;

                }
            }}
        return false;}


    @Override
    public Equipe addEquipe(Equipe equipe,Integer idUser) {
        Equipe existingEquipeOptional = equipeRepository.findByNomEquipe(equipe.getNomEquipe());
        if (existingEquipeOptional!=null) {log.warn("nom équipe existe!");return  null; }
        else{
            if (!isUserAlreadyInTeam(idUser)) {
                User user = userRepository.findById(idUser).orElse(null);
                equipe.setChefEquipe(user);
                Set<User> membresEquipe = new HashSet<>();
                membresEquipe.add(user);
                equipe.setMembresEquipe(membresEquipe);

                return equipeRepository.save(equipe);
            }
            else log.warn("User already selected ");
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
    public Equipe demandeAdhesion(Long idequipe, Integer iduser) {
        User user = userRepository.findById(iduser).orElse(null);
        Equipe equipe= equipeRepository.findById(idequipe).orElse(null);
        if (equipe.getMembresEquipe().size() < equipe.getNbMemEquipe()) {
            if (!isUserAlreadyInTeam(iduser)) {
                equipe.getMembresEnAttente().add(user);
                return equipeRepository.save(equipe);}
            else{
                log.warn("user already in team");
            }
        }
        else  {
            log.warn("L'equipe est complete ");}

        return null;
    }

    @Override
    public Equipe traiterAdhesion(Long idequipe,Integer userId, String reponse){
        Equipe equipe = equipeRepository.findById(idequipe).orElse(null);
        Set<User> pendingMembers = equipe.getMembresEnAttente();
        if (pendingMembers == null ||equipe.getChefEquipe() == null ) {
            log.warn("errors");}
        for (User user : pendingMembers) {
            User selectedUser = userRepository.findById(user.getId()).orElse(null);
            if (equipe.getMembresEquipe().size() < equipe.getNbMemEquipe()) {
                if (selectedUser.getId() == userId && reponse.equals("accepted")) {
                    equipe.getMembresEquipe().add(user);
                    equipe.getMembresEnAttente().remove(selectedUser);
                    return equipeRepository.save(equipe);
                } else if (selectedUser.getId() == userId && reponse.equals("refused")) {
                    equipe.getMembresEnAttente().remove(selectedUser);
                    return equipeRepository.save(equipe);
                }
            } else {
                log.warn("L'equipe est complete ");
            }
        }
        return null;
    }
    @Override
    public Equipe getByNom(String nomEquipe) {
        Equipe equipe= equipeRepository.findByNomEquipe(nomEquipe);
        if (equipe == null) {
            log.warn("Pas d'équipe avec ce nom");
        }
        return equipe;
    }

    @Override
    public Page<Equipe> getAllPagination(Pageable pageable){
        return  equipeRepository.findAll(pageable);

    }
}
