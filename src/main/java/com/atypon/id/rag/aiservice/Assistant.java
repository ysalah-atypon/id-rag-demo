package com.atypon.id.rag.aiservice;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService
public interface Assistant {

    @SystemMessage("You are a polite assistant")
    String chat(@MemoryId int memoryId, @UserMessage String userMessage);
}
