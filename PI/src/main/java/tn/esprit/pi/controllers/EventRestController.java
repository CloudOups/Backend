package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pi.entities.Event;
import tn.esprit.pi.services.EventServices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
 @AllArgsConstructor
 @RestController
 @RequestMapping("/event")
 public class EventRestController {
        EventServices eventServices;

        public static String uploadDirectory= System.getProperty("user.dir")+"/src/main/webapp/images";

        @PostMapping("/add")
        public Event addEvent(@ModelAttribute Event event, @RequestParam("img")MultipartFile imgg)throws IOException
        {
            String filename= imgg.getOriginalFilename();
            Path fileNameAndPath= Paths.get(uploadDirectory,filename);
            Files.write(fileNameAndPath,imgg.getBytes());
            event.setImage(filename);
            return eventServices.add(event);
        }

        @PutMapping("/update")
        public Event updateEvent(@RequestBody Event event){
            return eventServices.add(event);
        }

        @DeleteMapping("/delete/{idEvent}")
        public void deleteEvent(@PathVariable Long idEvent){
            eventServices.delete(idEvent);
        }

        @GetMapping("/get/{idEvent}")
        public Event getEventById(@PathVariable Long idEvent){
            return eventServices.getById(idEvent);
        }

        @GetMapping("/get/all")
        public List<Event> getAll(){
            return eventServices.getAll();
        }


}
