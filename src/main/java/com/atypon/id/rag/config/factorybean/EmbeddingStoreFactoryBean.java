package com.atypon.id.rag.config.factorybean;

import com.atypon.id.rag.embedding.store.SolrEmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Collections;

//@Component
public class EmbeddingStoreFactoryBean implements FactoryBean<EmbeddingStore> {

    private final Environment env;

    public EmbeddingStoreFactoryBean(Environment env) {
        this.env = env;
    }

    @Override
    public EmbeddingStore getObject() throws Exception {
        throw new IllegalArgumentException("Embedding is currently unsupported");
    }

    @Override
    public Class<?> getObjectType() {
        return EmbeddingStore.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
