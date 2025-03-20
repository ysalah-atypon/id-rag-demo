package com.atypon.id.rag.config.factorybean;

import com.atypon.id.rag.content.store.ContentStore;
import com.atypon.id.rag.content.store.SolrStore;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class FullTextContentStoreFactoryBean implements FactoryBean<ContentStore> {
    private final Environment env;

    public FullTextContentStoreFactoryBean(Environment env) {
        this.env = env;
    }

    @Override
    public ContentStore getObject() throws Exception {
        //todo:: set the hosts & check the possibility to support multi clients
        String zkHosts = env.getProperty("solr.zk.hosts");
        String zkChroot = env.getProperty("solr.zk.chroot");

        return SolrStore.builder().setZkHosts(Collections.singletonList(zkHosts)).setZkChroot(zkChroot).build();
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
