package org.llm.openai;

import java.util.Map;

public interface GenerativeAIFactory {
    GenerativeAIService create(String url, Map<String, Object> properties);


}
