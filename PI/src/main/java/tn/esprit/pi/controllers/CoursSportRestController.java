package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.Abonnement;
import tn.esprit.pi.entities.CoursSport;
import tn.esprit.pi.services.ICoursServices;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/coursSport")
@RestController
public class CoursSportRestController {
    private ICoursServices coursServices;

    @PostMapping("/add")
    public CoursSport addCoursSport(@RequestBody CoursSport coursSport){return  coursServices.addCoursSport(coursSport);}

    @PutMapping("/update")
    public CoursSport updateCoursSport(@RequestBody CoursSport coursSport){return  coursServices.updateCoursSport(coursSport);}
    @GetMapping ("/get/{idcours}")
    public CoursSport getCoursSport(@PathVariable Long idcours){return  coursServices.getById(idcours);}
    @DeleteMapping("/delete/{idcours}")
    public void removeCoursSport(@PathVariable Long idcours){ coursServices.delete(idcours);}

    @GetMapping("/all")
    public List<CoursSport> getAll(){return coursServices.getAll();}
}
