package org.llm.openai;

import java.util.HashMap;
import java.util.Map;

public class GenerativeAIDriverManager {

    private static final Map<String, GenerativeAIFactory> drivers = new HashMap<>();

    public static void registerService(String serviceName, GenerativeAIFactory factory) {
        drivers.put(serviceName, factory);
    }

    public static GenerativeAIService create(String serviceType, String url, Map<String, Object> properties) {

        var factory = drivers.get(serviceType);
        //check for null and throw exception
        if (factory == null) {
            throw new RuntimeException("No driver found for service type: " + serviceType);
        }
        return factory.create(url, properties);
    }
}
