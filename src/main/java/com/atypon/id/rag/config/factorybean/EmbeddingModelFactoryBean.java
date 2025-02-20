package com.atypon.id.rag.config.factorybean;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.mistralai.MistralAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EmbeddingModelFactoryBean implements FactoryBean<EmbeddingModel> {

    private final Environment env;

    public EmbeddingModelFactoryBean(Environment env) {
        this.env = env;
    }

    @Override
    public EmbeddingModel getObject() throws Exception {
        String modelType = env.getProperty("embedding.model.type");
        if ("openai".equalsIgnoreCase(modelType)) {
            return OpenAiEmbeddingModel.builder()
                    .apiKey(env.getProperty("openai.embedding.api-key"))
                    .modelName(env.getProperty("openai.embedding.model.name"))
                    .dimensions(768)//this doesn't seem to be used it always uses 1536D
                    .build();
        } else if("mistral".equalsIgnoreCase(modelType)){
            return  MistralAiEmbeddingModel.builder()
                    .apiKey(env.getProperty("mistral.embedding.api-key"))
                    .modelName("mistral-embed")
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
