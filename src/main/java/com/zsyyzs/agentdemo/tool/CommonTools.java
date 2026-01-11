package com.zsyyzs.agentdemo.tool;
import dev.langchain4j.agent.tool.Tool;
import java.time.LocalDateTime;

public class CommonTools {
    /**
     * 注意的 3 点（非常重要）：
     * @Tool = 对 LLM 暴露的能力
     * 方法签名 = Tool Schema
     * 注释越清晰，Agent 越聪明
     */

    @Tool("获取当前系统时间")
    public String getCurrentTime() {
        return LocalDateTime.now().toString();
    }

    @Tool("计算两个整数的加法")
    public int add(int a, int b) {
        return a + b;
    }
}
