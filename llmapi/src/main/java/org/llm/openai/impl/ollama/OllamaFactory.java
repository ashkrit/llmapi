package org.llm.openai.impl.ollama;

import org.llm.openai.GenerativeAIFactory;
import org.llm.openai.GenerativeAIService;

import java.util.Map;

public class OllamaFactory implements GenerativeAIFactory {

    public static final String NAME = "ollama";

    @Override
    public GenerativeAIService create(String url, Map<String, Object> properties) {
        return new OllamaGenerativeAIService(url, properties);
    }

}
