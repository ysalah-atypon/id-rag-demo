package com.atypon.id.rag.content.store;

import dev.langchain4j.rag.content.Content;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SolrStore implements ContentStore {

    private final SolrClient solrClient;
    private final String collectionName = "JB";
    private static final Logger log = LoggerFactory.getLogger(SolrStore.class);

    public SolrStore(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    public List<Content> search(FullTextSearchRequest searchRequest) {
        SolrQuery query = new SolrQuery("doi", searchRequest.doi());
        //todo: set the needed fields from solr to be returned
        query.setFields();

        try {
            QueryResponse queryResponse = solrClient.query(collectionName, query);
            assert queryResponse.getResults().size() == 1;
            return getContent(queryResponse.getResults());
        } catch (SolrServerException | IOException e) {
            //todo: better exception handling
            throw new RuntimeException(e);
        }
    }

    private List<Content> getContent(SolrDocumentList results) {
        return results.stream().map(result -> new FullTextDocument((String) result.get("fulltext"), "", "", new ArrayList<>())).collect(Collectors.toList());
    }

    public static SolrStoreBuilder builder(){
        return new SolrStoreBuilder();
    }


    public static class SolrStoreBuilder {
        private List<String> zkHosts;
        private String zkChroot;


        public SolrStore build() {
            SolrClient solrClient = new CloudSolrClient.Builder(zkHosts, Optional.of(zkChroot)).build();
            return new SolrStore(solrClient);
        }

        public SolrStoreBuilder setZkHosts(List<String> zkHosts) {
            this.zkHosts = zkHosts;
            return this;
        }

        public SolrStoreBuilder setZkChroot(String zkChroot) {
            this.zkChroot = zkChroot;
            return this;
        }
    }
}
