package com.zsyyzs.agentdemo.rag.config;

import com.zsyyzs.agentdemo.rag.agent.RagAgent;
import com.zsyyzs.agentdemo.rag.tool.RagTool;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RagAgentConfig {

    @Bean
    public RagAgent ragAgent(RagTool ragTool) {

        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("text-embedding-3-small")
                .build();

        return AiServices.builder(RagAgent.class)
                .chatLanguageModel(model)
                .tools(ragTool)
                .build();
    }

    @Bean
    public RagTool ragTool(
            dev.langchain4j.store.embedding.EmbeddingStore<String> store,
            dev.langchain4j.model.embedding.EmbeddingModel model
    ) {
        return new RagTool(store, model);
    }
}

