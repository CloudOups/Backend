package tn.esprit.pi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pi.entities.Publication;
import tn.esprit.pi.services.PublicationService;


import java.util.List;
@CrossOrigin(origins = "*")
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


    //upload image
    @PostMapping("/upload/{id}")
    public Publication handleFileUpload(@RequestParam("photo") MultipartFile file, @PathVariable("id") long publicationcode) {

        return publicationService.storeFile(file,publicationcode);
    }
    //affichage image
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName) {
        Resource resource = publicationService.loadFileAsResource(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}