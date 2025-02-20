package com.atypon.id.rag.config.factorybean;

import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class EmbeddingStoreIngestorFactoryBean implements FactoryBean<EmbeddingStoreIngestor> {
    private static final Logger logger = LoggerFactory.getLogger(EmbeddingStoreIngestorFactoryBean.class);

    private final EmbeddingStore embeddingStore;
    private final EmbeddingModel embeddingModel;

    @Lazy
    public EmbeddingStoreIngestorFactoryBean( EmbeddingStore embeddingStore, EmbeddingModel embeddingModel) {
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
    }

    @Override
    public EmbeddingStoreIngestor getObject() throws Exception {
        logger.info("Creating EmbeddingStoreIngestor with embeddingStore: {} and embeddingModel: {}", embeddingStore, embeddingModel);
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

    @Override
    public boolean isSingleton() {
        return true;
    }
}
