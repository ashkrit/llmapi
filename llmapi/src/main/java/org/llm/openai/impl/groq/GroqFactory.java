package org.llm.openai.impl.groq;

import org.llm.openai.GenerativeAIFactory;
import org.llm.openai.GenerativeAIService;

import java.util.Map;

public class GroqFactory implements GenerativeAIFactory {

    public static final String NAME="groq";
    @Override
    public GenerativeAIService create(String url, Map<String, Object> properties) {
        return new GroqGenerativeAIService(url, properties);
    }

}
