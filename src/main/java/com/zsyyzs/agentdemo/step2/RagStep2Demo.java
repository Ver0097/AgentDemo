package com.zsyyzs.agentdemo.step2;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.splitter.DocumentByCharacterSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import java.util.List;

public class RagStep2Demo {

    public static void main(String[] args) {


        String text = """
                LangChain4j is a Java framework for building AI agents.
                It supports RAG, tools, memory, and workflows.
                Java developers can build production AI systems.
                """;

        // 1️⃣ Document
        Document document = Document.from(text);

        // 2️⃣ Split（注意：没有 RecursiveTextSplitter）
        var splitter = new DocumentByCharacterSplitter(100, 20);
        List<TextSegment> segments = splitter.split(document);

        // 3️⃣ Embedding Model
        OpenAiEmbeddingModel embeddingModel =
                OpenAiEmbeddingModel.builder()
                        .baseUrl("http://langchain4j.dev/demo/openai/v1")
                        .apiKey("demo")
                        .modelName("text-embedding-3-small")
                        .build();

        // 4️⃣ Store
        EmbeddingStore<TextSegment> store =
                new InMemoryEmbeddingStore<>();

        // 5️⃣ Ingest
        segments.forEach(segment -> {
            store.add(
                    embeddingModel.embed(segment).content(),
                    segment
            );
        });

        // 6️⃣ Search（重点：新 API）
        EmbeddingSearchRequest request =
                EmbeddingSearchRequest.builder()
                        .queryEmbedding(
                                embeddingModel.embed("Java agent framework").content()
                        )
                        .maxResults(2)
                        .build();

        var result = store.search(request);

        System.out.println("🔍 搜索结果：");
        result.matches().forEach(match -> {
            System.out.println("----");
            System.out.println(match.embedded().text());
        });
    }
}

/**
 * 代码分析：RAG（检索增强生成）系统实现
 * 这个代码实现了一个基础的RAG（Retrieval-Augmented Generation）系统第二步，用于演示如何使用向量存储和语义搜索来查找相关信息。
 * 主要功能模块
 * 1. 文档处理
 * 文档创建：将文本内容转换为 Document 对象
 * 文本分割：使用 DocumentByCharacterSplitter 将文档按字符分割成段落（每段100个字符，重叠20个字符）
 * 2. 嵌入模型配置
 * OpenAI嵌入模型：配置为使用 text-embedding-3-small 模型
 * 演示模式：使用演示API密钥连接到LangChain4J的演示服务器
 * 3. 向量存储系统
 * 内存存储：创建 InMemoryEmbeddingStore 来存储文本段落及其向量表示
 * 数据注入：将每个文本段落转换为向量并存储
 * 4. 语义搜索功能
 * 查询嵌入：将搜索词 "Java agent framework" 转换为向量
 * 相似度匹配：在向量存储中查找最相似的2个结果
 * 结果展示：输出匹配的文本段落
 * 核心流程
 * 原始文本 → 分割成段落 → 向量化 → 存储到向量库 → 查询向量化 → 相似度搜索 → 返回匹配结果
 * 应用场景
 * 这个系统典型应用于：
 * 文档问答系统：用户提问时快速找到相关文档部分
 * 知识库检索：基于语义而非关键词的智能搜索
 * AI助手增强：为大语言模型提供上下文信息
 */