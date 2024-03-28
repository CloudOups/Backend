package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Abonnement;
import tn.esprit.pi.entities.CoursSport;
import tn.esprit.pi.repositories.IAbonnementRepository;
import tn.esprit.pi.repositories.ICoursSportRepository;

import java.util.List;
@AllArgsConstructor
@Service
public class CoursServices implements ICoursServices {
    ICoursSportRepository coursSportRepository;

    public CoursSport updateCoursSport(CoursSport coursSport) {return coursSportRepository.save(coursSport);}

    public CoursSport addCoursSport(CoursSport coursSport) { return coursSportRepository.save(coursSport); }

    public void delete( Long idcours) { coursSportRepository.deleteById(idcours);}

    public List<CoursSport> getAll() {return (List<CoursSport>) coursSportRepository.findAll();}
    public CoursSport getById(Long idcours) { return coursSportRepository.findById(idcours).orElse(null);}
}
