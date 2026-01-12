package com.zsyyzs.agentdemo.rag;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;





import java.util.List;

public class RagService {

//    private final EmbeddingStore<TextSegment> embeddingStore;
//    private final OpenAiEmbeddingModel embeddingModel;
//
//    /*文档 → 切分
//
//    切分 → 向量
//
//    存入向量库
//
//    查询 → TopK*/
//
//    public RagService() {
//        this.embeddingStore = new InMemoryEmbeddingStore<>();
//        this.embeddingModel = OpenAiEmbeddingModel.builder()
//                .apiKey(System.getenv("OPENAI_API_KEY"))
//                .modelName("text-embedding-3-small")
//                .build();
//        ingest();
//    }
//
//    private void ingest() {
//
//        // 1️⃣ 文档切分器（显式、可控、工程级）
//        RecursiveTextSplitter splitter = RecursiveTextSplitter.builder()
//                .maxChunkSize(200)
//                .overlapSize(20)
//                .build();
//
//        // 2️⃣ 文档 → TextSegment（关键变化）
//        List<TextSegment> segments =
//                DocumentLoader.loadDocuments()
//                        .stream()
//                        .flatMap(doc -> splitter.split(doc).stream())
//                        .toList();
//
//        // 3️⃣ 向量化并存储
//        for (TextSegment segment : segments) {
//            Embedding embedding =
//                    embeddingModel.embed(segment.text()).content();
//            embeddingStore.add(embedding, segment);
//        }
//    }
//
//
//    public List<TextSegment> search(String query) {
//        Embedding queryEmbedding = embeddingModel.embed(query).content();
//        return embeddingStore
//                .findRelevant(queryEmbedding, 3)
//                .stream()
//                .map(match -> match.embedded())
//                .toList();
//    }
}

