package com.zsyyzs.agentdemo.step4.rag.tool;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingStore;

public class RagTool {

    private final EmbeddingStore<String> store;
    private final EmbeddingModel embeddingModel;

    public RagTool(EmbeddingStore<String> store,
                   EmbeddingModel embeddingModel) {
        this.store = store;
        this.embeddingModel = embeddingModel;
    }

    @Tool("从知识库中搜索相关内容")
    public String search(String question) {

        Embedding queryEmbedding =
                embeddingModel.embed(question).content();

        var result = store.search(
                EmbeddingSearchRequest.builder()
                        .queryEmbedding(queryEmbedding)
                        .maxResults(3)
                        .build()
        );

        return result.matches().stream()
                .map(m -> m.embedded())
                .reduce("", (a, b) -> a + "\n" + b);
    }
}
/**
 * Agent 把「查资料」当成一个工具
 * LLM 自己决定要不要用
 */