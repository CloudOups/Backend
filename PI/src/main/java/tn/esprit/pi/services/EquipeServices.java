package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Equipe;
import tn.esprit.pi.entities.MembresEquipe;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repositories.IEquipeRepository;
import tn.esprit.pi.repositories.IMembresEquipeRepository;
import tn.esprit.pi.repositories.IUserRepository;
import tn.esprit.pi.repositories.UserRepository;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class EquipeServices implements IEquipeServices{
IEquipeRepository equipeRepository;
IUserRepository userRepository;
IMembresEquipeRepository membresEquipeRepository;
   /* @Override
    public Equipe addEquipe(Equipe equipe,Long idUser) {
        User user =userRepository.findById(idUser).orElse(null);
        equipe.setChef(user);
        equipe = equipeRepository.save(equipe);
        MembresEquipe newMember = new MembresEquipe();
        newMember.setEquipe(equipe); // Set the Equipe
        newMember.setUser(user);
        MembresEquipe membresEquipe= membresEquipeRepository.save(newMember);
        equipe.getMembresEquipe().add(membresEquipe);
        return equipeRepository.save(equipe);
    }*/
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

    //    membresEquipeRepository.deleteByEquipeId(equipeID);
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
        // Add the user to the equipe's list of pending members
       Equipe equipe= equipeRepository.findById(idequipe).orElse(null);
        if (equipe.getMembresEquipe().size() < equipe.getNbMemEquipe()) {
            equipe.getPendingMembers().add(user);
        // Save the updated equipe
        return equipeRepository.save(equipe);}
        else  {               System.out.println("L'equipe est complete ");}

        return null;
    }

    @Override
    public Equipe traiterAdhesion(Long idequipe,Integer userId, String reponse){
        Equipe equipe = equipeRepository.findById(idequipe).orElse(null);
        Set<User> pendingMembers = equipe.getPendingMembers();
        if (pendingMembers == null ||equipe.getChef() == null ) {
            System.out.println("errors");
    }

        for (User user : pendingMembers) {
            User selectedUser = userRepository.findById(user.getId()).orElse(null);
            if (equipe.getMembresEquipe().size() < equipe.getNbMemEquipe()) {
           /*    if (selectedUser.getUserId() == userId && reponse.equals("accepted")) {
                    MembresEquipe newMember = new MembresEquipe();
                    newMember.setEquipe(equipe);
                    newMember.setUser(selectedUser);
                    MembresEquipe membresEquipe = membresEquipeRepository.save(newMember);
                    equipe.getMembresEquipe().add(membresEquipe);
                    // equipe.setMembresEquipe((Set<MembresEquipe>) selectedUser) ;
                    equipe.getPendingMembers().remove(selectedUser);
                    return equipeRepository.save(equipe);
                } else if (selectedUser.getUserId() == userId && reponse.equals("refused")) {
                    equipe.getPendingMembers().remove(selectedUser);*/
                if (selectedUser.getId() == userId && reponse.equals("accepted")) {
                    equipe.getMembresEquipe().add(user);
                    equipe.getMembresEnAttente().remove(selectedUser);
                    return equipeRepository.save(equipe);
                } else if (selectedUser.getId() == userId && reponse.equals("refused")) {
                    equipe.getMembresEnAttente().remove(selectedUser);
                    return equipeRepository.save(equipe);
                }
            } else {
                System.out.println("L'equipe est complete ");
            }
        }
        return null;

    @Override
    public Page<Equipe> getAllPagination(Pageable pageable){
        return  equipeRepository.findAll(pageable);

    }
}
