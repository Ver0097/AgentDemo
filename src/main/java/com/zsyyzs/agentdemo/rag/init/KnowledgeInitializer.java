package com.zsyyzs.agentdemo.rag.init;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class KnowledgeInitializer {

    private final EmbeddingStore<String> store;
    private final EmbeddingModel embeddingModel;

    public KnowledgeInitializer(EmbeddingStore<String> store,
                                EmbeddingModel embeddingModel) {
        this.store = store;
        this.embeddingModel = embeddingModel;
    }

    @PostConstruct
    public void init() {
        add("LangChain4j 是一个 Java 的 LLM / Agent 框架");
        add("RAG 是 Retrieval Augmented Generation");
        add("Agent 可以根据问题决定是否查询知识库");
    }

    private void add(String text) {
        Embedding embedding = embeddingModel.embed(text).content();
        store.add(embedding, text);
    }
}
/**
 * 👉 这一步等价于：
 *
 * 解析 PDF / 数据库 / Wiki
 *
 * 转 Embedding
 *
 * 存向量库
 */