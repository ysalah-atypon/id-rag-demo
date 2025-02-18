package com.atypon.id.rag.config.factorybean;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EmbeddingModelFactoryBean implements FactoryBean<EmbeddingModel> {

    @Autowired
    private Environment env;

    @Override
    public EmbeddingModel getObject() throws Exception {
        String modelType = env.getProperty("embedding.model.type");
        if ("openai".equalsIgnoreCase(modelType)) {
            return OpenAiEmbeddingModel.builder()
                    .apiKey(env.getProperty("openai.embedding.api-key"))
                    .modelName(env.getProperty("openai.embedding.model.name"))
                    .build();
        } else {
            throw new IllegalArgumentException("Unsupported chat model type: " + modelType);
        }

    }

    @Override
    public Class<?> getObjectType() {
        return EmbeddingModel.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
