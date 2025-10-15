package br.com.occhi.suporte.services;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

/**
 * Interface do serviço de IA para assistente de suporte de vendas.
 * 
 * Esta interface define o contrato para o assistente virtual baseado em
 * LangChain4j, que atua como um agente de suporte ao cliente inteligente.
 * 
 * O assistente é configurado com:
 * - Personalidade e comportamento definidos via @SystemMessage
 * - Capacidade de manter contexto por sessão via @MemoryId
 * - Integração com ferramentas (tools) para consulta de dados
 * - Processamento de linguagem natural para interações humanas
 * 
 * Funcionalidades do assistente:
 * - Responder perguntas sobre pedidos
 * - Auxiliar com cancelamentos
 * - Fornecer informações sobre produtos
 * - Manter conversas contextualizadas
 * - Validar identidade do usuário
 * 
 * @author Ailton Occhi
 * @version 1.0
 * @since 2025
 */
@AiService
public interface AssistenteSuporteVendas {
	
	/**
	 * Processa uma mensagem do usuário e retorna uma resposta do assistente.
	 * 
	 * Este método é o ponto de entrada principal para interações com o
	 * assistente de IA. Ele:
	 * 
	 * 1. Recebe a mensagem do usuário
	 * 2. Mantém o contexto da conversa usando o memoryId
	 * 3. Aplica as regras definidas no @SystemMessage
	 * 4. Pode utilizar ferramentas (tools) para buscar dados
	 * 5. Retorna uma resposta contextualizada e personalizada
	 * 
	 * O @SystemMessage define a personalidade e regras do assistente:
	 * - Nome: "Robozinho"
	 * - Sistema: "Venda Fácil"
	 * - Comportamento: amigável, educado, profissional e conciso
	 * - Validações de segurança para acesso a pedidos
	 * - Limitação de escopo para tópicos relacionados ao sistema
	 * 
	 * Regras de negócio implementadas:
	 * 1. Validação de identidade antes de mostrar detalhes de pedidos
	 * 2. Respostas limitadas ao domínio do sistema de pedidos
	 * 3. Tratamento educado para perguntas fora do escopo
	 * 4. Transparência quando não tem informações disponíveis
	 * 
	 * @param memoryId identificador único da sessão para manter contexto da conversa
	 * @param userMessage mensagem enviada pelo usuário
	 * @return Result contendo a resposta processada pelo assistente de IA
	 */
	@SystemMessage("""
			Seu nome é Robozinho e você é assistente de suporte ao cliente de um sistema de pedidos chamado "Venda Fácil".
			Você é amigável, educado, profissional e conciso.

			Regras que você deve seguir:

			1. Antes de obter os detalhes do pedido ou cancelá-lo,
			você deve se certificar de saber o nome, sobrenome e ID do pedido do usuário.

			2. Responda apenas a perguntas relacionadas ao sistema de pedidos e seus serviços.
			Se for perguntado algo não relacionado, explique gentilmente que você só pode ajudar com tópicos relacionados ao pedido.

			3. Se não tiver certeza de algo, responda educadamente e informe ao cliente que você não tem essa informação.

			Hoje é {{current_date}}.
			     """)
	Result<String> answer(@MemoryId String memoryId, @UserMessage String userMessage);
}