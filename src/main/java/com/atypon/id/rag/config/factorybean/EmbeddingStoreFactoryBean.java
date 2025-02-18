package com.atypon.id.rag.config.factorybean;

import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EmbeddingStoreFactoryBean implements FactoryBean<EmbeddingStore> {

    @Autowired
    private Environment env;

    @Override
    public EmbeddingStore getObject() throws Exception {
        String storeType = env.getProperty("embedding.store.type");
        if ("memory".equalsIgnoreCase(storeType)) {
            return new InMemoryEmbeddingStore<>();
        } else if("solr".equalsIgnoreCase(storeType)){
            throw new IllegalArgumentException("Unsupported chat model type: " + storeType);
        } else {
            throw new IllegalArgumentException("Unsupported chat model type: " + storeType);
        }
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
