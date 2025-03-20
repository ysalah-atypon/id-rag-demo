package com.atypon.id.rag.content.store;

import java.util.List;

public class FullTextSearchResult {
    private final List<FullTextDocument> matches;

    public FullTextSearchResult(List<FullTextDocument> matches) {
        this.matches = matches;
    }

    public List<FullTextDocument> matches() {
        return matches;
    }
}
