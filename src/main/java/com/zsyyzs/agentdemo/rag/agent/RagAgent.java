package com.zsyyzs.agentdemo.rag.agent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface RagAgent {

    @SystemMessage("""
        你是一个企业知识助手。
        如果问题涉及专业知识，请先搜索知识库再回答。
        """)
    String chat(@UserMessage String question);
}
