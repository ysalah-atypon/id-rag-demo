package com.atypon.id.rag.config.factorybean;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ChatModelFactoryBean implements FactoryBean<ChatLanguageModel> {


    private final Environment env;

    public ChatModelFactoryBean(Environment env) {
        this.env = env;
    }

    @Override
    public ChatLanguageModel getObject() throws Exception {
        String modelType = env.getProperty("chat.model.type");

        if ("openai".equalsIgnoreCase(modelType)) {
            return OpenAiChatModel.builder()
                    .apiKey(env.getProperty("openai.api-key"))
                    .modelName(env.getProperty("openai.model.name"))
                    .temperature(Double.parseDouble(env.getProperty("openai.temperature")))
                    .build();
        } else if ("ollama".equalsIgnoreCase(modelType)) {
            return OllamaChatModel.builder()
                    .baseUrl(env.getProperty("ollama.baseUrl"))
                    .modelName(env.getProperty("ollama.model.name"))
                    .temperature(Double.parseDouble(env.getProperty("ollama.temperature")))
                    .build();
        } else if("gemini".equalsIgnoreCase(modelType)){
            return GoogleAiGeminiChatModel.builder()
                    .apiKey(env.getProperty("gemini.api-key"))
                    .modelName(env.getProperty("gemini.model.name"))
                    .logRequestsAndResponses(true).build();

        }
        else {
            throw new IllegalArgumentException("Unsupported chat model type: " + modelType);
        }
    }

    @Override
    public Class<?> getObjectType() {
        return ChatLanguageModel.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}