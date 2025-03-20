package com.atypon.id.rag.content;

import java.util.Objects;

public class ChatMemoryID {
    private String userId;
    private String product;
    private String doi;
    private String uuid;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMemoryID that = (ChatMemoryID) o;
        return Objects.equals(userId, that.userId) && Objects.equals(product, that.product) && Objects.equals(doi, that.doi) && Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, product, doi, uuid);
    }
}
