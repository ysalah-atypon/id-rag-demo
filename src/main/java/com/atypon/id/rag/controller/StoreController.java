package com.atypon.id.rag.controller;

import com.atypon.id.Docs;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.IngestionResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreController {
    EmbeddingStoreIngestor embeddingStoreIngestor;

    StoreController(EmbeddingStoreIngestor embeddingStoreIngestor) {
        this.embeddingStoreIngestor = embeddingStoreIngestor;
    }

    @GetMapping("/store")
    public String store(){
        Document document = Document.from(Docs.text1);
        IngestionResult ingest = embeddingStoreIngestor.ingest(document);
        return ingest.toString();
    }


}
