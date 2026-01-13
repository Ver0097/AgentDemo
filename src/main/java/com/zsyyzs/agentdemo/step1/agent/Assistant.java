package com.zsyyzs.agentdemo.step1.agent;

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


}
