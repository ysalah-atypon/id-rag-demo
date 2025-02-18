package com.atypon.id.rag.config.factorybean;

import dev.langchain4j.rag.content.injector.ContentInjector;
import dev.langchain4j.rag.content.injector.DefaultContentInjector;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class ContentInjectorFactoryBean implements FactoryBean<ContentInjector> {
    @Override
    public ContentInjector getObject() throws Exception {
        return DefaultContentInjector.builder()
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return ContentInjector.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
