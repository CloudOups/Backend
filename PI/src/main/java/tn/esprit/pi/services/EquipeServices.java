package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Equipe;
import tn.esprit.pi.repositories.IEquipeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class EquipeServices implements IEquipeServices{
IEquipeRepository equipeRepository;
    @Override
    public Equipe addEquipe(Equipe equipe) {
        return equipeRepository.save(equipe);
    }

    @Override
    public Equipe updateEquipe(Equipe equipe) {
        return equipeRepository.save(equipe);
    }

    @Override
    public void delete(int equipeID) {
         equipeRepository.deleteById(equipeID);
    }

    @Override
    public Equipe getById(int equipeID) {
        return equipeRepository.findById(equipeID).get();
    }

    @Override
    public List<Equipe> getAll() {
        return (List<Equipe>)equipeRepository.findAll();
    }
}
