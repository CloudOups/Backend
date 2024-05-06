package tn.esprit.pi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tn.esprit.pi.dto.requests.ChatBotResponseDto;
import tn.esprit.pi.entities.Equipe;
import tn.esprit.pi.entities.Event;
import tn.esprit.pi.entities.StatusTerrain;
import tn.esprit.pi.entities.Terrain;
import tn.esprit.pi.repositories.IEquipeRepository;
import tn.esprit.pi.repositories.IEventRepository;
import tn.esprit.pi.repositories.ITerrainRepository;

import java.util.ArrayList;
import java.util.List;
@Service
public class ChatBotService {
    @Autowired
    private IEventRepository eventRepository;

    @Autowired
    private IEquipeRepository equipeRepository;

    @Autowired
    private ITerrainRepository terrainRepository;

    public ChatBotResponseDto generateResponse(String message) {

        ChatBotResponseDto responseDto = new ChatBotResponseDto();
        message = message.toLowerCase();
        List<Event> events = eventRepository.findAll();
        List<Equipe> clubs = (List<Equipe>) equipeRepository.findAll();
        List<Terrain> terrains = (List<Terrain>) terrainRepository.findAll();
        List<Terrain> terrainsDisponibles = new ArrayList<>();
        StringBuilder response = new StringBuilder();

        if (message.contains("événement") || message.contains("evenement") ) {
            if (!CollectionUtils.isEmpty(events)) {
                response.append("Parmi les événements que nous avons programmés : ");
                for (Event event : events) {
                    response.append(event.getNomevent()).append(", ");
                }
            } else {
                response.append("Nous n'avons pas encore planifié d'événements.");
            }
        }

        if (message.contains("club") || message.contains("equipe") || message.contains("équipe")) {
            if (!CollectionUtils.isEmpty(clubs)) {
                response.append("Voici la liste des clubs sportifs : ");
                for (Equipe club : clubs) {
                    response.append(club.getNomEquipe()).append(", ");
                }
            } else {
                response.append("Nous n'avons pas encore de clubs sportifs.");
            }
        }

        if (message.contains("terrain") || message.contains("stade")) {
            response.append("Voici la liste des terrains au sein de l'université : ");
            for (Terrain terrain : terrains) {
                response.append(terrain.getNomTerrain()).append(" (").append(terrain.getTypeTerrain()).append("), ");
                if (StatusTerrain.Libre.equals(terrain.getStatusTerrain())) {
                    terrainsDisponibles.add(terrain);
                }
            }
            if (!terrainsDisponibles.isEmpty()) {
                response.append("\n\nTerrains disponibles : ");
                for (Terrain terrainDisponible : terrainsDisponibles) {
                    response.append(terrainDisponible.getNomTerrain()).append(" (").append(terrainDisponible.getTypeTerrain()).append("), ");
                }
            } else {
                response.append("\n\nTous les terrains sont actuellement occupés.");
            }
        }
        String answer = response.toString();
        if (StringUtils.isEmpty(answer)) {
            answer = "Désolé, je ne comprends pas votre demande. vous pouvez me questionner sur les clubs, les terrains et les evenements au sein de l'université";
        }
        responseDto.setResponse(answer);
        return responseDto;
    }
}

