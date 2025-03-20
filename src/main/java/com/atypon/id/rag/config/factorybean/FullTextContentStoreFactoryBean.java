package com.atypon.id.rag.config.factorybean;

import com.atypon.id.rag.content.store.ContentStore;
import com.atypon.id.rag.content.store.SolrStore;
import org.springframework.beans.factory.FactoryBean;

import java.util.Collections;

public class FullTextContentStoreFactoryBean implements FactoryBean<ContentStore> {
    @Override
    public ContentStore getObject() throws Exception {
        //todo:: set the hosts & check the possibility to support multi clients
        return SolrStore.builder().setZkHosts(Collections.EMPTY_LIST).setZkChroot("").build();
    }

    @Override
    public Class<?> getObjectType() {
        return ContentStore.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
