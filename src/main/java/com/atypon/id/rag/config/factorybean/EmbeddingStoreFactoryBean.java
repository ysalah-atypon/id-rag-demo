package com.atypon.id.rag.config.factorybean;

import com.atypon.id.rag.embedding.store.SolrEmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class EmbeddingStoreFactoryBean implements FactoryBean<EmbeddingStore> {

    private final Environment env;

    public EmbeddingStoreFactoryBean(Environment env) {
        this.env = env;
    }

    @Override
    public EmbeddingStore getObject() throws Exception {
        String storeType = env.getProperty("embedding.store.type");
        if ("memory".equalsIgnoreCase(storeType)) {
            return new InMemoryEmbeddingStore<>();
        } else if ("solr".equalsIgnoreCase(storeType)) {
            String collectionName = env.getProperty("solr.collection.name");
            String zkHosts = env.getProperty("solr.zk.hosts");
            String zkChroot = env.getProperty("solr.zk.chroot");
            return SolrEmbeddingStore.builder().setCollectionName(collectionName).setZkHosts(Collections.singletonList(zkHosts)).setZkChroot(zkChroot).build();
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
