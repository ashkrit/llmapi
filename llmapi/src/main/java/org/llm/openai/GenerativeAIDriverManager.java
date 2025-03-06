package org.llm.openai;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GenerativeAIDriverManager {

    private static final ConcurrentMap<String, GenerativeAIFactory> drivers = new ConcurrentHashMap<>();

    public static void registerService(String serviceName, GenerativeAIFactory factory) {
        drivers.put(serviceName, factory);
    }

    // remove service
    public static void removeService(String serviceName) {
        drivers.remove(serviceName);
    }

    public static GenerativeAIService create(String serviceType, String url, Map<String, Object> properties) {

        var factory = drivers.get(serviceType);
        //check for null and throw exception
        if (factory == null) {
            throw new RuntimeException("No driver found for service type: " + serviceType);
        }
        return factory.create(url, properties);
    }

    //services List<String>
    public static String[] getServices() {
        return drivers.keySet().toArray(new String[0]);
    }

}
