package org.llm.examples;

import com.google.gson.GsonBuilder;
import org.llm.openai.GenerativeAIDriverManager;
import org.llm.openai.impl.google.GoogleAIFactory;
import org.llm.openai.model.ChatRequest;

import java.util.List;
import java.util.Map;

import static org.llm.openai.GenerativeAIDriverManager.registerService;

public class TypeSafety {

    public static void main(String[] args) {

        registerService(GoogleAIFactory.NAME, new GoogleAIFactory());

        Map<String, Object> properties = Map.of("apiKey", System.getenv("gemma_key"));
        var service = GenerativeAIDriverManager.create(GoogleAIFactory.NAME, "https://generativelanguage.googleapis.com", properties);

        // Create a parameterized prompt
        String prompt = """
                Suggest a {{product_type}} product with {{feature_count}} features. Reply in JSON format
                
                <example>
                {
                    "productName": "product name",
                    "description": "product description",
                    "price": 100.0,
                    "features": ["feature 1", "feature 2", "feature 3", "feature 4", "feature 5"]
                }
                </example>
                """;
        var request = new ChatRequest(
                "gemini-2.0-flash",
                0.7f,
                List.of(new ChatRequest.ChatMessage("user",
                        prompt))
        );

        // Prepare with parameters
        Map<String, Object> params = Map.of(
                "product_type", "smart home",
                "feature_count", 5
        );
        request = service.prepareRequest(request, params);


        // Get typed response
        var suggestion = service.chat(request, ProductSuggestion.class);

        System.out.println(suggestion);
    }

    public static class ProductSuggestion {
        public String productName;
        public String description;
        public double price;
        public List<String> features;

        @Override
        public String toString() {
            return new GsonBuilder().setPrettyPrinting().create().toJson(this);
        }

    }


}
