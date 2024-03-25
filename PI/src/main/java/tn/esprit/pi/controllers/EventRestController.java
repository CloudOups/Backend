package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.Event;
import tn.esprit.pi.services.EventServices;

import java.util.List;
 @AllArgsConstructor
 @RestController
 @RequestMapping("/event")
 public class EventRestController {
        EventServices eventServices;
        @PostMapping("/add")
        public Event addEvent(@RequestBody Event event){
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
