package com.zsyyzs.agentdemo;

import com.zsyyzs.agentdemo.agent.Assistant;
import com.zsyyzs.agentdemo.tool.CommonTools;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AgentDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgentDemoApplication.class, args);
    }

    /**
     * 这里是 Agent 工程的核心
     * AiServices = Agent Runtime
     * tools() = Agent 可用能力
     * LLM 决定 是否 & 何时调用 Tool
     */

    @Bean
    public Assistant assistant() {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .modelName("gpt-4o-mini")
                .temperature(0.2)
                .build();

        return AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .tools(new CommonTools())
                .build();
    }

}
