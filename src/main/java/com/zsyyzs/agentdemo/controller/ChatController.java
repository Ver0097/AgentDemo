package com.zsyyzs.agentdemo.controller;
import com.zsyyzs.agentdemo.agent.Assistant;
import com.zsyyzs.agentdemo.rag.RagService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final Assistant assistant;
    private final RagService ragService = new RagService();


    public ChatController(Assistant assistant) {
        this.assistant = assistant;
    }

    @PostMapping
    public String chat(@RequestBody String message) {
        return assistant.chat(message);
    }


}
