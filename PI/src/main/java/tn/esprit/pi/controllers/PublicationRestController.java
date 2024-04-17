package tn.esprit.pi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.Publication;
import tn.esprit.pi.services.PublicationService;


import java.util.List;

@RequestMapping("/publication")
@RestController
public class PublicationRestController {



     @Autowired
    private  PublicationService publicationService;

    @PostMapping("/add")
    public Publication addPublication(@RequestBody Publication publication) {
        return publicationService.addPublication(publication);
    }

    @PutMapping("/update")
    public Publication updatePublication(@RequestBody Publication publication) {
        return publicationService.updatePublication(publication);
    }

    @DeleteMapping("/delete/{numPub}")
    public void deletePublication(@PathVariable Long numPub) {
        publicationService.deletePublication(numPub);
    }

    @GetMapping("/get/{numPub}")
    public Publication getPublication(@PathVariable Long numPub) {
        return publicationService.getById(numPub);
    }

    @GetMapping("/get/all")
    public List<Publication> getAllPublications() {
        return publicationService.getAll();
    }

    @PostMapping("/like/{numPub}")
    public Publication likePublication(@PathVariable Long numPub) {
        return publicationService.likePublication(numPub);
    }

    @PostMapping("/unlike/{numPub}")
    public Publication unlikePublication(@PathVariable Long numPub) {
        return publicationService.unlikePublication(numPub);
    }

}