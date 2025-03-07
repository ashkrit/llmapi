package org.llm.openai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GenerativeAIDriverManagerTest {

    private static class TestFactory implements GenerativeAIFactory {
        @Override
        public GenerativeAIService create(String url, Map<String, Object> properties) {
            return null;
        }
    }

    @BeforeEach
    void cleanup() {
        for (String service : GenerativeAIDriverManager.getServices()) {
            GenerativeAIDriverManager.removeService(service);
        }
    }

    @Test
    void testRegisterAndCreateService() {
        // Given
        var factory = new TestFactory();
        GenerativeAIDriverManager.registerService("test", factory);

        // When
        var service = GenerativeAIDriverManager.create("test", "url", Map.of());

        // Then
        assertNull(service);
        assertArrayEquals(new String[]{"test"}, GenerativeAIDriverManager.getServices());
    }

    @Test
    void testRemoveService() {
        // Given
        var factory = new TestFactory();
        GenerativeAIDriverManager.registerService("test", factory);

        // When
        GenerativeAIDriverManager.removeService("test");

        // Then
        assertArrayEquals(new String[]{}, GenerativeAIDriverManager.getServices());
    }

    @Test
    void testCreateWithUnknownService() {
        // When/Then
        var exception = assertThrows(RuntimeException.class,
            () -> GenerativeAIDriverManager.create("unknown", "url", Map.of()));
        assertEquals("No driver found for service type: unknown", exception.getMessage());
    }

    @Test
    void testMultipleServices() {
        // Given
        var factory1 = new TestFactory();
        var factory2 = new TestFactory();

        // When
        GenerativeAIDriverManager.registerService("test1", factory1);
        GenerativeAIDriverManager.registerService("test2", factory2);

        // Then
        var services = GenerativeAIDriverManager.getServices();
        assertEquals(2, services.length);
        assertTrue(services[0].startsWith("test"));
        assertTrue(services[1].startsWith("test"));
    }
}