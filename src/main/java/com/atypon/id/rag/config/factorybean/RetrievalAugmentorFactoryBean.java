package com.atypon.id.rag.config.factorybean;

import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.injector.ContentInjector;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class RetrievalAugmentorFactoryBean implements FactoryBean<RetrievalAugmentor> {

    private final ContentRetriever contentRetriever;
    private final ContentInjector contentInjector;

    public RetrievalAugmentorFactoryBean(ContentRetriever contentRetriever, ContentInjector contentInjector) {
        this.contentRetriever = contentRetriever;
        this.contentInjector = contentInjector;
    }

    @Override
    public RetrievalAugmentor getObject() throws Exception {
        return DefaultRetrievalAugmentor.builder()
                .contentRetriever(contentRetriever)
                .contentInjector(contentInjector)
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return RetrievalAugmentor.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
