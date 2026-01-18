package com.zsyyzs.agentdemo.step1.controller;
import com.zsyyzs.agentdemo.step1.agent.Assistant;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final Assistant assistant;


    public ChatController(Assistant assistant) {
        this.assistant = assistant;
    }

    @PostMapping
    public String chat(@RequestBody String message) {
        return assistant.chat(message);
    }


}
