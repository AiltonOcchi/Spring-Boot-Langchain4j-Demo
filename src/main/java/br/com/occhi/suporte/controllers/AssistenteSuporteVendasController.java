package br.com.occhi.suporte.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.occhi.suporte.services.AssistenteSuporteVendas;
import dev.langchain4j.service.Result;

/**
 * Controller REST responsável pela comunicação com o assistente virtual de suporte.
 * 
 * Esta classe expõe endpoints HTTP para interação com o assistente de IA,
 * permitindo que clientes externos (frontend, aplicativos mobile, etc.)
 * conversem com o sistema de suporte automatizado.
 * 
 * O assistente é capaz de:
 * - Responder perguntas sobre pedidos
 * - Fornecer informações sobre produtos
 * - Auxiliar com cancelamentos de pedidos
 * - Manter contexto da conversa por sessão
 * 
 * @author Ailton Occhi
 * @version 1.0
 * @since 2025
 */
@RestController
public class AssistenteSuporteVendasController {
	
	/**
	 * Serviço do assistente de suporte de vendas.
	 * Injetado automaticamente pelo Spring Boot.
	 */
	private final AssistenteSuporteVendas assistentesuporteVendas;
	
	/**
	 * Construtor para injeção de dependência.
	 * 
	 * @param assistentesuporteVendas instância do serviço do assistente de IA
	 */
	public AssistenteSuporteVendasController( AssistenteSuporteVendas assistentesuporteVendas ) {
		this.assistentesuporteVendas = assistentesuporteVendas;
	}

	/**
	 * Endpoint para conversar com o assistente virtual.
	 * 
	 * Este endpoint permite que usuários enviem mensagens para o assistente
	 * e recebam respostas processadas pela IA. Cada sessão mantém seu próprio
	 * contexto de conversa.
	 * 
	 * Exemplos de uso:
	 * - GET /chat?sessionId=user123&message=Qual o status do meu pedido 456?
	 * - GET /chat?sessionId=user123&message=Quero cancelar meu pedido
	 * 
	 * @param sessionId identificador único da sessão do usuário para manter contexto
	 * @param message mensagem/pergunta do usuário para o assistente
	 * @return resposta processada pelo assistente de IA
	 */
	@GetMapping("/chat")
	public String chat(@RequestParam String sessionId, @RequestParam String message) {
		Result<String> result = assistentesuporteVendas.answer(sessionId, message);
		return result.content();
	}
}