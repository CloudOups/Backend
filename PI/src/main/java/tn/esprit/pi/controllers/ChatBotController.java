package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pi.dto.requests.ChatBotResponseDto;
import tn.esprit.pi.services.ChatBotService;

@RequestMapping("/chatbot")
@AllArgsConstructor
@RestController
public class ChatBotController {

    @Autowired
    private ChatBotService chatBotService;

    @PostMapping("/send")
    public ResponseEntity<ChatBotResponseDto> sendMessage(@RequestBody String message) {



        ChatBotResponseDto responseDto = new ChatBotResponseDto();
        responseDto  = chatBotService.generateResponse(message);
        return ResponseEntity.ok().body(responseDto);
    }

}

