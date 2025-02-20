package com.atypon.id.rag.embedding.service;

import com.atypon.id.rag.content.DocumentConstants;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.IngestionResult;
import org.springframework.stereotype.Service;

@Service
public class EmbeddingStoreService {
    private final EmbeddingStoreIngestor embeddingStoreIngestor;

    public EmbeddingStoreService(EmbeddingStoreIngestor embeddingStoreIngestor) {
        this.embeddingStoreIngestor = embeddingStoreIngestor;
    }

    public String store(String text) {
        Document document = Document.from(text);
        IngestionResult ingest = embeddingStoreIngestor.ingest(document);
        return ingest.toString();
    }

    public String store(String fullText, String doi) {
        Document document = Document.from(fullText, Metadata.metadata(DocumentConstants.DOI, doi));
        IngestionResult ingest = embeddingStoreIngestor.ingest(document);
        return ingest.toString();
    }
}
