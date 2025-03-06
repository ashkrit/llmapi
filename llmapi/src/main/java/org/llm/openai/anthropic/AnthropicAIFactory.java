package org.llm.openai.anthropic;

import org.llm.openai.GenerativeAIFactory;
import org.llm.openai.GenerativeAIService;

import java.util.Map;

public class AnthropicAIFactory implements GenerativeAIFactory {

    @Override
    public GenerativeAIService create(String url, Map<String, Object> properties) {
        return new AnthropicGenerativeAIService(url, properties);
    }
}
