package com.atypon.id.rag.content.store;

import com.atypon.id.rag.content.DocumentConstants;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.ContentMetadata;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record FullTextDocument(String fulltext,String doi, String title, List<String> authors) implements Content {


    @Override
    public TextSegment textSegment() {
        Metadata metadata = new Metadata();
        metadata.put(DocumentConstants.AUTHORS, normalizeAuthors(authors));
        metadata.put(DocumentConstants.TITLE, title );
        metadata.put(DocumentConstants.DOI, doi);
        return TextSegment.from(fulltext, metadata);
    }

    private String normalizeAuthors(List<String> authors) {
       return authors.stream()
                .map( Object::toString )
                .collect( Collectors.joining( "," ) );
    }

    @Override
    public Map<ContentMetadata, Object> metadata() {
        return Map.of();
    }
}
