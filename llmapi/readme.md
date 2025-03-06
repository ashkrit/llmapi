
# Java LLM Client

A Java client library for interacting with various Large Language Model (LLM) APIs including OpenAI, Anthropic, Google, Groq, and Ollama.

## Maven Coordinates

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>org.llm</groupId>
    <artifactId>llmapi</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Features

- Unified interface for multiple LLM providers
- Support for chat completions
- Support for embeddings (where available)
- Factory pattern for service creation
- Environment-based API key configuration

## Supported Providers

- OpenAI (GPT-4)
- Anthropic (Claude)
- Google (Gemma)
- Groq
- Ollama (Local deployment)

## Prerequisites

- Java 17 or higher
- Maven
- API keys for respective services
- Ollama running locally (for Ollama support)


## Usage Example

```java
// Register AI service providers
registerService(OpenAIFactory.NAME, new OpenAIFactory());
registerService(OllamaFactory.NAME, new OllamaFactory());

// Create a service instance
Map<String, Object> properties = Map.of("apiKey", System.getenv("gpt_key"));
var service = GenerativeAIDriverManager.create(
    OpenAIFactory.NAME, 
    "https://api.openai.com/", 
    properties
);

// Chat completion
var messages = new ChatMessage("user", "Hello, how are you?");
var conversation = new ChatRequest("gpt-4o-mini", List.of(messages));
var reply = service.chat(conversation);
System.out.println(reply.message());

// Generate embeddings
var vector = service.embedding(
    new EmbeddingRequest("text-embedding-3-small", "How are you")
);
System.out.println(Arrays.toString(vector.embedding()));
```

## Project Structure

- `GenerativeAIService`: Main interface for AI operations
- `GenerativeAIFactory`: Factory interface for service creation
- `GenerativeAIDriverManager`: Service registry and creation manager
- Provider-specific implementations in respective packages

## Models

- OpenAI: gpt-4o-mini, text-embedding-3-small
- Anthropic: claude-3-7-sonnet-20250219
- Google: gemma
- Groq: llama-3.3-70b-versatile
- Ollama: llama3.2, all-minilm


## How to Register a Service Provider



## Supported Factories and Registration

The following AI service factories are supported out of the box:

```java
// OpenAI
registerService(OpenAIFactory.NAME, new OpenAIFactory());

// Anthropic
registerService(AnthropicAIFactory.NAME, new AnthropicAIFactory());

// Google
registerService(GoogleAIFactory.NAME, new GoogleAIFactory());

// Groq
registerService(GroqFactory.NAME, new GroqFactory());

// Ollama (Local)
registerService(OllamaFactory.NAME, new OllamaFactory());
```

### Service Configuration

Each provider requires specific configuration:

#### OpenAI
```java
Map<String, Object> properties = Map.of("apiKey", System.getenv("gpt_key"));
var service = GenerativeAIDriverManager.create(
    OpenAIFactory.NAME, 
    "https://api.openai.com/", 
    properties
);
```

#### Anthropic
```java
Map<String, Object> properties = Map.of("apiKey", System.getenv("ANTHROPIC_API_KEY"));
var service = GenerativeAIDriverManager.create(
    AnthropicAIFactory.NAME, 
    "https://api.anthropic.com", 
    properties
);
```

#### Google
```java
Map<String, Object> properties = Map.of("apiKey", System.getenv("gemma_key"));
var service = GenerativeAIDriverManager.create(
    GoogleAIFactory.NAME, 
    "https://generativelanguage.googleapis.com", 
    properties
);
```

#### Groq
```java
Map<String, Object> properties = Map.of("apiKey", System.getenv("gorq_key"));
var service = GenerativeAIDriverManager.create(
    GroqFactory.NAME, 
    "https://api.groq.com/openai", 
    properties
);
```

#### Ollama (Local)
```java
Map<String, Object> properties = Map.of();
var service = GenerativeAIDriverManager.create(
    OllamaFactory.NAME, 
    "http://localhost:11434", 
    properties
);
```

### Factory Constants

Access factory names using these constants:
```java
OpenAIFactory.NAME      // "openai"
AnthropicAIFactory.NAME // "anthropic"
GoogleAIFactory.NAME    // "google"
GroqFactory.NAME        // "groq"
OllamaFactory.NAME      // "ollama"
