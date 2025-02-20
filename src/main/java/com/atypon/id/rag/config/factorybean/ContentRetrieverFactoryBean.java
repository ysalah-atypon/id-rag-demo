package com.atypon.id.rag.config.factorybean;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ContentRetrieverFactoryBean implements FactoryBean<ContentRetriever> {


    private final EmbeddingStore embeddingStore;
    private final EmbeddingModel embeddingModel;

    @Lazy
    public ContentRetrieverFactoryBean(EmbeddingStore embeddingStore, EmbeddingModel embeddingModel) {
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
    }

    @Override
    public ContentRetriever getObject() throws Exception {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return ContentRetriever.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
