package tn.esprit.pi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.Publication;
import tn.esprit.pi.services.PublicationService;


import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/publication")
@RestController
public class PublicationRestController {


@Autowired
    private  PublicationService publicationService;

    @PostMapping("/add")
    public Publication addPublication(@RequestBody Publication publication) {
        return publicationService.addPublication(publication);
    }

    @PutMapping("/update/{id}")
    public Publication updatePublication(@RequestBody Publication publication,@PathVariable long id) {
        return publicationService.updatePublication(publication,id);
    }

    @DeleteMapping("/delete/{numPub}")
    public void deletePublication(@PathVariable Long numPub) {
        publicationService.deletePublication(numPub);
    }

    @GetMapping("/getpublication/{numPub}")
    public Publication getPublication(@PathVariable Long numPub) {
        return publicationService.getById(numPub);
    }

    @GetMapping("/getall")
    public List<Publication> getAllPublications() {
        return publicationService.getAll();
    }
    @GetMapping("/getallapproved")
    public List<Publication> getAllApprovedPublications(){ return publicationService.getAllApprovedPublications();}


    @GetMapping("/getallunapproved")
    public List<Publication> getAllUnApprovedPublications(){ return publicationService.getAllUnapprovedPublications();}

    @PostMapping("/like/{numPub}")
    public void likePublication(@PathVariable Long numPub) {
         publicationService.likePublication(numPub);
    }

    @PostMapping("/unlike/{numPub}")
    public void unlikePublication(@PathVariable Long numPub) {
         publicationService.unlikePublication(numPub);
    }

    @GetMapping("/get/{numPub}")
    public Publication getPublicationById(@PathVariable Long numPub) {
        return publicationService.getById(numPub);
    }
    @PutMapping("/approve/{id}")
    public Publication approveBlog(@PathVariable long id) {
        return publicationService.ApproveBlog(id);
    }

    @PutMapping("/approveAll")
    public List<Publication> approveAllBlogs() {
        return publicationService.ApproveAllBlogs();
    }
}