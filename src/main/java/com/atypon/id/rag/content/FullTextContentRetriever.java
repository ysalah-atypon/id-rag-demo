package com.atypon.id.rag.content;

import com.atypon.id.rag.content.store.ContentStore;
import com.atypon.id.rag.content.store.FullTextSearchRequest;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.Query;


import java.util.List;

public class FullTextContentRetriever implements ContentRetriever {
    private ContentStore contentStore;

    public FullTextContentRetriever(ContentStore contentStore) {
        this.contentStore= contentStore;
    }

    @Override
    public List<Content> retrieve(Query query) {
        ChatMemoryID chatMemoryID = (ChatMemoryID) query.metadata().chatMemoryId();
        FullTextSearchRequest searchRequest = FullTextSearchRequest.builder().client(chatMemoryID.getProduct()).doi(chatMemoryID.getDoi()).build();
        return contentStore.search(searchRequest);
    }

    public static FullTextContentRetrieverBuilder builder() {
        return new FullTextContentRetrieverBuilder();
    }

    public static class FullTextContentRetrieverBuilder {
        private ContentStore contentStore;
        public FullTextContentRetrieverBuilder contentStore(ContentStore contentStore){
            this.contentStore = contentStore;
            return this;
        }

        public FullTextContentRetriever build(){
            return new FullTextContentRetriever(contentStore);
        }
    }
}
