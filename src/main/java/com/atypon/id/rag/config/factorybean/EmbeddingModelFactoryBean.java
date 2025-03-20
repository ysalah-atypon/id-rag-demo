package com.atypon.id.rag.config.factorybean;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.mistralai.MistralAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

//@Component
public class EmbeddingModelFactoryBean implements FactoryBean<EmbeddingModel> {

    private final Environment env;

    public EmbeddingModelFactoryBean(Environment env) {
        this.env = env;
    }

    @Override
    public EmbeddingModel getObject() throws Exception {
        throw new IllegalArgumentException("Embeddings is currently unsupported");

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
