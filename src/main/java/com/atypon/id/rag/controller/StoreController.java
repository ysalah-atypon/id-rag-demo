package com.atypon.id.rag.controller;

import com.atypon.id.Docs;
import com.atypon.id.rag.embedding.service.EmbeddingStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreController {
    @Autowired
    EmbeddingStoreService embeddingStoreService;

    @GetMapping("/store")
    public String store(@RequestParam(value = "doi")String doi){
        return embeddingStoreService.store(Docs.text1, doi);
    }

}
