package com.atypon.id.rag.content.store;

import dev.langchain4j.rag.content.Content;

import java.util.List;

public interface ContentStore {
    public List<Content> search(FullTextSearchRequest searchRequest);
}
