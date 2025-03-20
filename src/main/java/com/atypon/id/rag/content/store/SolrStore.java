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
    private final String collectionName = "jb";
    private static final Logger LOGGER = LoggerFactory.getLogger(SolrStore.class);

    public SolrStore(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    public List<Content> search(FullTextSearchRequest searchRequest) {
//        SolrQuery query = new SolrQuery("DOI:"+ searchRequest.doi());
        SolrQuery query = new SolrQuery("doi:" + searchRequest.doi());
        query.setFields("Fulltext_en", "Title_en", "DOI");

        try {
            QueryResponse queryResponse = solrClient.query(collectionName, query);
            return getContent(queryResponse.getResults());
        } catch (SolrServerException | IOException e) {
            //todo: better exception handling
            throw new RuntimeException(e);
        }
    }

    private List<Content> getContent(SolrDocumentList results) {
        return results.stream().map(result -> {
            String doi = (String) result.get("DOI");
            Optional<String> title = ((ArrayList<String>) result.get("Title_en")).stream().findFirst();
            Optional<String> fulltext = ((ArrayList<String>) result.get("Fulltext_en")).stream().findFirst();
            return new FullTextDocument(fulltext.orElse(""), doi, title.orElse(""), new ArrayList<>());
        }).collect(Collectors.toList());
    }

    public static SolrStoreBuilder builder() {
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
