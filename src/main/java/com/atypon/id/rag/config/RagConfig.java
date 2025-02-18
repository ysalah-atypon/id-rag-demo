package com.atypon.id.rag.config;


import com.atypon.id.rag.config.factorybean.*;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.injector.ContentInjector;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

@Configuration
public class RagConfig {

    @Bean(name = "embeddingModel")
    EmbeddingModel embeddingModel(EmbeddingModelFactoryBean factory) throws Exception {
        return factory.getObject();
    }

    @Bean(name = "embeddingStore")
    EmbeddingStore embeddingStore(EmbeddingStoreFactoryBean factory) throws Exception {
        return factory.getObject();
    }

    @Bean(name = "chatLanguageModel")
    ChatLanguageModel chatLanguageModel (ChatModelFactoryBean factory) throws Exception {
        return factory.getObject();
    }

    @ConditionalOnBean({EmbeddingModel.class, EmbeddingStore.class})
    @Bean(name = "contentRetriever")
    ContentRetriever contentRetriever(ContentRetrieverFactoryBean factory) throws Exception {
        return factory.getObject();
    }

    @ConditionalOnBean({EmbeddingModel.class, EmbeddingStore.class})
    @Bean(name = "embeddingStoreIngestor")
    EmbeddingStoreIngestor embeddingStoreIngestor(EmbeddingStoreIngestorFactoryBean factory) throws Exception {
        return factory.getObject();
    }

    @ConditionalOnBean({ContentRetriever.class, ContentInjector.class})
    @Bean(name = "retrievalAugmentor")
    RetrievalAugmentor retrievalAugmentor(RetrievalAugmentorFactoryBean factory) throws Exception {
        return factory.getObject();
    }

    @Bean(name = "contentInjector")
    ContentInjector contentInjector(ContentInjectorFactoryBean factory) throws Exception {
        return factory.getObject();
    }

    @Bean(name = "chatMemoryProvider")
    ChatMemoryProvider chatMemoryProvider(Tokenizer tokenizer) {
        return memoryId -> TokenWindowChatMemory.builder()
                .id(memoryId)
                .maxTokens(5000, tokenizer)
                .build();
    }

    @Bean(name = "tokenizer")
    @ConditionalOnMissingBean
    Tokenizer tokenizer() {
        return new OpenAiTokenizer(GPT_4_O_MINI.toString());
    }

    @Bean(name = "chatMemory")
    ChatMemory chatMemory() {
        return MessageWindowChatMemory.withMaxMessages(10);
    }

    @Bean(name = "chatModelListener")
    ChatModelListener chatModelListener() {
        return new ChatModelListener() {

            private static final Logger log = LoggerFactory.getLogger(ChatModelListener.class);

            @Override
            public void onRequest(ChatModelRequestContext requestContext) {
                log.info("onRequest(): {}", requestContext.chatRequest());
            }

            @Override
            public void onResponse(ChatModelResponseContext responseContext) {
                log.info("onResponse(): {}", responseContext.chatResponse());
            }

            @Override
            public void onError(ChatModelErrorContext errorContext) {
                log.info("onError(): {}", errorContext.error().getMessage());
            }
        };
    }
}
