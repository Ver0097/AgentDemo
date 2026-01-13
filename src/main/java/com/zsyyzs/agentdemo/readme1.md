一、这个 Demo 能做什么？

用户问：

“现在几点了？顺便告诉我 10 + 20 等于多少”

Agent 会：

判断需要 调用工具

调 getCurrentTime

调 add

汇总回答

👉 这就是 最小 Agent 闭环

二、项目结构（标准后端风格）
langchain4j-agent-demo
├── pom.xml
└── src/main/java/com/example/agent
├── AgentDemoApplication.java
├── controller
│   └── ChatController.java
├── agent
│   └── Assistant.java
└── tool
└── CommonTools.java

三、pom.xml（重点依赖）
<dependencies>
<!-- Spring Boot -->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
</dependency>

    <!-- LangChain4j -->
    <dependency>
        <groupId>dev.langchain4j</groupId>
        <artifactId>langchain4j</artifactId>
        <version>0.31.0</version>
    </dependency>

    <!-- OpenAI -->
    <dependency>
        <groupId>dev.langchain4j</groupId>
        <artifactId>langchain4j-open-ai</artifactId>
        <version>0.31.0</version>
    </dependency>
</dependencies>


⚠️ 注意：

Java 17+

LangChain4j 版本尽量保持一致

四、Tool 定义（Agent 的“手”）
package com.example.agent.tool;

import dev.langchain4j.agent.tool.Tool;

import java.time.LocalDateTime;

public class CommonTools {

    @Tool("获取当前系统时间")
    public String getCurrentTime() {
        return LocalDateTime.now().toString();
    }

    @Tool("计算两个整数的加法")
    public int add(int a, int b) {
        return a + b;
    }
}

这里你要注意的 3 点（非常重要）：

@Tool = 对 LLM 暴露的能力

方法签名 = Tool Schema

注释越清晰，Agent 越聪明

五、Agent 接口（核心）
package com.example.agent.agent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface Assistant {

    @SystemMessage("""
        你是一个智能助理，
        可以使用工具来获取信息或完成计算。
        如果问题需要工具，请优先使用工具。
        """)
    String chat(@UserMessage String message);
}


👉 Agent 的“人格 + 行为约束”就在这里

六、Spring Boot 启动 & Agent 装配（重点）
package com.example.agent;

import com.example.agent.agent.Assistant;
import com.example.agent.tool.CommonTools;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AgentDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgentDemoApplication.class, args);
    }

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

这里是 Agent 工程的核心

AiServices = Agent Runtime

tools() = Agent 可用能力

LLM 决定 是否 & 何时调用 Tool

七、Controller（像普通后端一样用 Agent）
package com.example.agent.controller;

import com.example.agent.agent.Assistant;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final Assistant assistant;

    public ChatController(Assistant assistant) {
        this.assistant = assistant;
    }

    @PostMapping
    public String chat(@RequestBody String message) {
        return assistant.chat(message);
    }
}

八、如何运行 & 测试
1️⃣ 设置环境变量
export OPENAI_API_KEY=sk-xxx

2️⃣ 启动 Spring Boot
3️⃣ 请求
POST http://localhost:8080/chat


Body：

现在几点了？顺便算一下 10 + 20

4️⃣ 你会看到类似回复
现在时间是 2026-01-11T20:15:30，
10 + 20 的结果是 30。


👉 中间 Agent 实际调用了 2 个 Tool

九、你此时已经跨过了哪道门槛？

✅ Java → LLM
✅ Tool Calling
✅ Agent 推理
✅ Spring 工程化
✅ 可扩展架构

到这里，你已经不是“ChatGPT 调用者”，
而是 Agent 系统工程师