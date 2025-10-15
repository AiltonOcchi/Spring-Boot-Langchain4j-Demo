package br.com.occhi.suporte.config;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.Tokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do agente de IA para o assistente de suporte de vendas.
 * 
 * Esta classe configura os componentes necessários para o funcionamento
 * do assistente virtual baseado em LangChain4j, incluindo o gerenciamento
 * de memória das conversas.
 * 
 * A memória das conversas é implementada usando uma janela de tokens,
 * permitindo que o assistente mantenha contexto das interações anteriores
 * dentro de um limite de tokens definido.
 * 
 * @author Ailton Occhi
 * @version 1.0
 * @since 2025
 */
@Configuration
public class AgentConfiguration {
	
	/**
	 * Configura o provedor de memória de chat para o assistente de IA.
	 * 
	 * Este bean é responsável por:
	 * - Criar instâncias de memória de chat para cada sessão/usuário
	 * - Limitar o contexto de cada conversa a 5000 tokens
	 * - Manter o histórico da conversa dentro do limite de tokens
	 * - Garantir que cada sessão tenha sua própria memória isolada
	 * 
	 * A memória funciona como uma janela deslizante: quando o limite de tokens
	 * é atingido, as mensagens mais antigas são removidas para dar espaço
	 * às novas mensagens.
	 * 
	 * @param tokenizer tokenizador usado para contar tokens nas mensagens
	 * @return provedor de memória de chat configurado
	 */
	@Bean
	ChatMemoryProvider chatMemoryProvider( Tokenizer tokenizer) {
		return memoryId -> TokenWindowChatMemory.builder()
				.id(memoryId)
				.maxTokens(5000, tokenizer)
				.build();
	}
}
