package com.atypon.id.rag.config.factorybean;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
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
        if ("gemini".equalsIgnoreCase(modelType)) {
            return GoogleAiGeminiChatModel.builder()
                    .apiKey(env.getProperty("gemini.api-key"))
                    .modelName(env.getProperty("gemini.model.name"))
                    .logRequestsAndResponses(true).build();

        } else {
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