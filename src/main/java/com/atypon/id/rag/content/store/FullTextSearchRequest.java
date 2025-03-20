package com.atypon.id.rag.content.store;

public class FullTextSearchRequest {
    private String doi;
    private String client;

    FullTextSearchRequest(String client, String doi) {
        this.doi = doi;
        this.client = client;
    }

    public String doi() {
        return doi;
    }

    public String getClient() {
        return client;
    }

    public static FullTextSearchRequestBuilder builder(){
        return new FullTextSearchRequestBuilder();
    }

    public static class FullTextSearchRequestBuilder {
        private String doi;
        private String client;

        public FullTextSearchRequestBuilder doi(String doi) {
            this.doi = doi;
            return this;
        }
        public FullTextSearchRequestBuilder client(String client) {
            this.client = client;
            return this;
        }

        public FullTextSearchRequest build(){
            return new FullTextSearchRequest(this.client, this.doi);
        }
    }
}
