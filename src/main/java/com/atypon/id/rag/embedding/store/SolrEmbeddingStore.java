package com.atypon.id.rag.embedding.store;

import com.atypon.id.rag.content.DocumentConstants;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.filter.Filter;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static dev.langchain4j.internal.Utils.isNullOrEmpty;
import static dev.langchain4j.internal.Utils.randomUUID;
import static dev.langchain4j.internal.ValidationUtils.ensureTrue;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;


//todo: implement the remove and search methods
public class SolrEmbeddingStore implements EmbeddingStore<TextSegment> {

    private final SolrClient solrClient;
    private final String collectionName;
    private static final Logger log = LoggerFactory.getLogger(SolrEmbeddingStore.class);

    public SolrEmbeddingStore(SolrClient solrClient, String collectionName) {
        this.solrClient = solrClient;
        this.collectionName = collectionName;
    }

    @Override
    public String add(Embedding embedding) {
        String id = randomUUID();
        add(id, embedding);
        return id;
    }

    @Override
    public void add(String id, Embedding embedding) {
        try {
            SolrInputDocument document = new SolrInputDocument();
            document.addField(DocumentConstants.ID, id);
            document.addField(DocumentConstants.VECTOR, embedding.vector());
            UpdateResponse response = solrClient.add(collectionName, document);
            solrClient.commit(collectionName);
            System.out.println("Document indexed: " + response);
        } catch (Exception e) {
            throw new RuntimeException("Error indexing document in Solr", e);
        }
    }

    @Override
    public String add(Embedding embedding, TextSegment textSegment) {
        String id = randomUUID();
        addInternal(id, embedding, textSegment);
        return id;
    }

    private void addInternal(String id, Embedding embedding, TextSegment embedded) {
        addAll(singletonList(id), singletonList(embedding), embedded == null ? null : singletonList(embedded));
    }

    @Override
    public void remove(String id) {
        EmbeddingStore.super.remove(id);
    }

    @Override
    public void removeAll(Filter filter) {
        EmbeddingStore.super.removeAll(filter);
    }

    @Override
    public void removeAll() {
        EmbeddingStore.super.removeAll();
    }

    @Override
    public EmbeddingSearchResult search(EmbeddingSearchRequest request) {
        return EmbeddingStore.super.search(request);
    }

    @Override
    public void removeAll(Collection ids) {
        EmbeddingStore.super.removeAll(ids);
    }

    @Override
    public void addAll(List<String> ids, List<Embedding> embeddings, List<TextSegment> embedded) {
        if (isNullOrEmpty(ids) || isNullOrEmpty(embeddings)) {
            log.info("[do not add empty embeddings to solr]");
            return;
        }
        ensureTrue(ids.size() == embeddings.size(), "ids size is not equal to embeddings size");
        ensureTrue(embedded == null || embeddings.size() == embedded.size(), "embeddings size is not equal to embedded size");

        try {
            bulkIndex(ids, embeddings, embedded);
        } catch (Exception e) {
            throw new RuntimeException("Error indexing multiple documents in Solr", e);
        }
    }

    private void bulkIndex(List<String> ids, List<Embedding> embeddings, List<TextSegment> embedded) throws IOException {
        List<SolrInputDocument> documents = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField(DocumentConstants.ID, ids.get(i));
            document.addField(DocumentConstants.VECTOR, embeddings.get(i).vectorAsList());
            if (embedded != null) {
                document.addField(DocumentConstants.TEXT, embedded.get(i).text());
                document.addField(DocumentConstants.DOI, embedded.get(i).metadata().getString(DocumentConstants.DOI));
            }
            log.info("Document sent for index indexed: {}", document);
            documents.add(document);
        }
        try {
            UpdateResponse response = solrClient.add(collectionName, documents);
            solrClient.commit(collectionName);
            log.info("Documents indexed: {}", response);
        } catch (Exception e) {
            throw new RuntimeException("Error indexing multiple documents in Solr", e);
        }
    }


    @Override
    public List<String> addAll(List<Embedding> embeddings) {
        List<String> ids = embeddings.stream()
                .map(ignored -> randomUUID())
                .collect(toList());
        addAll(ids, embeddings, null);
        return ids;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<String> zkHosts;
        private String zkChroot;
        private String collectionName;

        public SolrEmbeddingStore build() {
            SolrClient solrClient = new CloudSolrClient.Builder(zkHosts, Optional. of("/id-solr-arrs")).build();
            return new SolrEmbeddingStore(solrClient, collectionName);
        }


        public Builder setCollectionName(String collectionName) {
            this.collectionName = collectionName;
            return this;
        }

        public Builder setZkHosts(List<String> zkHosts) {
            this.zkHosts = zkHosts;
            return this;
        }

        public Builder setZkChroot(String zkChroot) {
            this.zkChroot = zkChroot;
            return this;
        }
    }

}
