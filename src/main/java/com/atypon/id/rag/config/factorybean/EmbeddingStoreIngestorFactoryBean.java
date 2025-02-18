package com.atypon.id.rag.config.factorybean;

import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmbeddingStoreIngestorFactoryBean implements FactoryBean<EmbeddingStoreIngestor> {


    @Autowired
    private EmbeddingStore embeddingStore;
    @Autowired
    private  EmbeddingModel embeddingModel;

    @Override
    public EmbeddingStoreIngestor getObject() throws Exception {
        return EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(300, 0))
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return EmbeddingStoreIngestor.class;
    }
}
