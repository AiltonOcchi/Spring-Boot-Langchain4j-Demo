package br.com.occhi.suporte.config;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.Tokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfiguration {
	@Bean
	ChatMemoryProvider chatMemoryProvider( Tokenizer tokenizer) {
		return memoryId -> TokenWindowChatMemory.builder()
				.id(memoryId)
				.maxTokens(5000, tokenizer)
				.build();
	}
}
