package org.llm.openai.google;

import org.llm.openai.GenerativeAIFactory;
import org.llm.openai.GenerativeAIService;
import org.llm.openai.anthropic.AnthropicGenerativeAIService;

import java.util.Map;

public class GoogleAIFactory implements GenerativeAIFactory {

    public static final String NAME="google";
    @Override
    public GenerativeAIService create(String url, Map<String, Object> properties) {
        return new GoogleGenerativeAIService(url, properties);
    }
}
