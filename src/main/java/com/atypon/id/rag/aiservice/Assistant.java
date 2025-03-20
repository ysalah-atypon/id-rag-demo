package com.atypon.id.rag.aiservice;

import com.atypon.id.rag.content.ChatMemoryID;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService
public interface Assistant {

    @SystemMessage("You are a polite assistant")
    Result chat(@MemoryId ChatMemoryID memoryId, @UserMessage String userMessage);
}
