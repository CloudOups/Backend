package tn.esprit.pi.services;

import tn.esprit.pi.entities.CoursSport;

import java.util.List;

public interface ICoursServices {
     CoursSport updateCoursSport(CoursSport coursSport);
     CoursSport addCoursSport(CoursSport coursSport);
     void delete( Long idcours);
     List<CoursSport> getAll();
     CoursSport getById(Long idcours);
}
