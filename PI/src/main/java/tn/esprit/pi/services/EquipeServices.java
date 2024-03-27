package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Equipe;
import tn.esprit.pi.entities.MembresEquipe;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repositories.IEquipeRepository;
import tn.esprit.pi.repositories.IMembresEquipeRepository;
import tn.esprit.pi.repositories.IUserRepository;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class EquipeServices implements IEquipeServices{
IEquipeRepository equipeRepository;
IUserRepository userRepository;
IMembresEquipeRepository membresEquipeRepository;
    @Override
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
    }

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
    public Equipe demandeAdhesion(Long idequipe, Long iduser) {
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
    public Equipe reponseAdhesion(Long idequipe,Long userId, String reponse){
        Equipe equipe = equipeRepository.findById(idequipe).orElse(null);
        Set<User> pendingMembers = equipe.getPendingMembers();
        if (pendingMembers == null ||equipe.getChef() == null ) {
            System.out.println("errors");
    }

        for (User user : pendingMembers) {
            User selectedUser = userRepository.findById(user.getUserId()).orElse(null);
            if (equipe.getMembresEquipe().size() < equipe.getNbMemEquipe()) {
                if (selectedUser.getUserId() == userId && reponse.equals("accepted")) {
                    MembresEquipe newMember = new MembresEquipe();
                    newMember.setEquipe(equipe);
                    newMember.setUser(selectedUser);
                    MembresEquipe membresEquipe = membresEquipeRepository.save(newMember);
                    equipe.getMembresEquipe().add(membresEquipe);
                    // equipe.setMembresEquipe((Set<MembresEquipe>) selectedUser) ;
                    equipe.getPendingMembers().remove(selectedUser);
                    return equipeRepository.save(equipe);
                } else if (selectedUser.getUserId() == userId && reponse.equals("refused")) {
                    equipe.getPendingMembers().remove(selectedUser);
                    return equipeRepository.save(equipe);
                }
            } else {
                System.out.println("L'equipe est complete ");
            }
        }
        return null;


    }

}
