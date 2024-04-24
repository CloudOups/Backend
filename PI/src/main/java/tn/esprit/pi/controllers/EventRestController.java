package tn.esprit.pi.controllers;

//import com.google.zxing.WriterException;
import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pi.entities.Event;
import tn.esprit.pi.services.EventServices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
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

     @GetMapping("/qr/{idEvent}")
     public String getQRCodeForEvent(@PathVariable Long idEvent) throws IOException, WriterException {
         Event event = eventServices.getById(idEvent);
         if (event == null) {
             return "Event not found";
         }
         byte[] qrCodeBytes = eventServices.generateQRCode(event.toString(), 250, 250);
         String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCodeBytes);
         return "data:image/png;base64," + qrCodeBase64;
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
