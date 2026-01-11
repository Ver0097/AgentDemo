package com.zsyyzs.agentdemo.agent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface Assistant {

    /**
     * Agent 的“人格 + 行为约束”就在这里
     */

    @SystemMessage("""
        你是一个智能助理，
        可以使用工具来获取信息或完成计算。
        如果问题需要工具，请优先使用工具。
        """)
    String chat(@UserMessage String message);

    @SystemMessage("""
        你是公司的智能助手。
        你必须基于【给定资料】回答问题。
        如果资料中没有答案，请明确说不知道。
        """)
    String chat2(@UserMessage String message);
}
