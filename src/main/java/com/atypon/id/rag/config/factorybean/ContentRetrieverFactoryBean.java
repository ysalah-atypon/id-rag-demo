package com.atypon.id.rag.config.factorybean;

import com.atypon.id.rag.content.FullTextContentRetriever;
import com.atypon.id.rag.content.store.ContentStore;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ContentRetrieverFactoryBean implements FactoryBean<ContentRetriever> {
    private final ContentStore contentStore;

    @Lazy
    public ContentRetrieverFactoryBean(ContentStore contentStore){
        this.contentStore = contentStore;
    }

    @Override
    public ContentRetriever getObject() throws Exception {
        return FullTextContentRetriever.builder().contentStore(contentStore).build();
    }

    @Override
    public Class<?> getObjectType() {
        return ContentRetriever.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
