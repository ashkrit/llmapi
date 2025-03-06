package org.llm.openai.openai;

import org.llm.openai.GenerativeAIFactory;
import org.llm.openai.GenerativeAIService;

import java.util.Map;

public class OpenAIFactory implements GenerativeAIFactory {

    @Override
    public GenerativeAIService create(String url, Map<String, Object> properties) {
        return new OpenAIGenerativeAIService(url, properties);
    }

}
