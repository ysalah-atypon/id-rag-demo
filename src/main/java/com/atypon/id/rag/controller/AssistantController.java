package com.atypon.id.rag.controller;

import com.atypon.id.rag.aiservice.Assistant;
import com.atypon.id.rag.content.ChatMemoryID;
import dev.langchain4j.service.Result;
import org.springframework.web.bind.annotation.*;

@RestController
public class AssistantController {
    Assistant assistant;


    AssistantController(Assistant assistant) {
        this.assistant = assistant;

    }


    @PostMapping("/assistant")
    public String assistant(@RequestParam(value = "question") String question, @RequestBody ChatMemoryID memoryID) {
        return assistant.chat(memoryID, question).content();
    }

}
