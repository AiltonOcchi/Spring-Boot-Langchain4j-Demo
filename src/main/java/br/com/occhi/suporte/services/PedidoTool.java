package br.com.occhi.suporte.services;
import java.math.BigDecimal;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import br.com.occhi.suporte.enums.StatusPedido;
import br.com.occhi.suporte.records.DetalhesPedido;

/**
 * Classe que expõe ferramentas (tools) para o assistente de IA.
 * 
 * Esta classe atua como uma ponte entre o assistente de IA (LangChain4j)
 * e os serviços de negócio da aplicação. Cada método anotado com @Tool
 * se torna uma ferramenta que o assistente pode utilizar para buscar
 * informações ou executar operações.
 * 
 * O padrão de Tools permite que o assistente de IA:
 * - Acesse dados em tempo real do sistema
 * - Execute operações específicas conforme necessário
 * - Forneça respostas baseadas em dados atuais
 * - Mantenha a separação entre IA e lógica de negócio
 * 
 * Características das ferramentas:
 * - Métodos simples e focados
 * - Parâmetros claramente definidos
 * - Retornos estruturados
 * - Delegação para serviços especializados
 * 
 * @author Ailton Occhi
 * @version 1.0
 * @since 2025
 */
@Component
public class PedidoTool {
	
	/**
	 * Serviço de pedidos para processamento da lógica de negócio.
	 * Injetado automaticamente pelo Spring Boot.
	 */
	private final PedidoService pedidoService;

	/**
	 * Construtor para injeção de dependência.
	 * 
	 * @param pedidoService instância do serviço de pedidos
	 */
	public PedidoTool(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	/**
	 * Ferramenta para obter quantidade de pedidos de um usuário.
	 * 
	 * Esta ferramenta permite que o assistente de IA consulte quantos
	 * pedidos um usuário específico possui no sistema. Útil para:
	 * - Fornecer estatísticas ao usuário
	 * - Validar histórico de compras
	 * - Contextualizar conversas de suporte
	 * 
	 * Exemplo de uso pelo assistente:
	 * "Você possui 5 pedidos registrados em nosso sistema."
	 * 
	 * @param usuarioId identificador único do usuário
	 * @return quantidade total de pedidos do usuário
	 */
	@Tool
	public Integer obterQuantidadePedidosPorUsuario(Long usuarioId) {
		return pedidoService.obterQuantidadePedidosPorUsuario(usuarioId);
	}

	/**
	 * Ferramenta para obter quantidade de pedidos por status.
	 * 
	 * Esta ferramenta permite que o assistente acesse estatísticas
	 * operacionais do sistema, como quantos pedidos estão:
	 * - NOVO: aguardando processamento
	 * - EM_ANDAMENTO: sendo processados
	 * - CONCLUIDO: finalizados com sucesso
	 * - CANCELADO: cancelados
	 * 
	 * Útil para responder perguntas sobre o estado geral do sistema.
	 * 
	 * @param status status do pedido para filtrar a contagem
	 * @return quantidade de pedidos com o status especificado
	 */
	@Tool
	public int obterQuantidadePedidosPorStatus(StatusPedido status) {
		return pedidoService.obterQuantidadePedidosPorStatus(status);
	}

	/**
	 * Ferramenta para obter o valor do pedido mais caro.
	 * 
	 * Esta ferramenta permite que o assistente forneça informações
	 * sobre estatísticas de vendas, como o valor do maior pedido
	 * já registrado no sistema.
	 * 
	 * Exemplo de uso pelo assistente:
	 * "O pedido mais caro já registrado foi de R$ 2.499,99."
	 * 
	 * @return valor total do pedido mais caro
	 */
	@Tool
	public BigDecimal obterValorPedidoMaisCaro() {
		return pedidoService.obterValorPedidoMaisCaro();
	}

	/**
	 * Ferramenta para obter detalhes de um pedido específico.
	 * 
	 * Esta é uma das ferramentas mais importantes, permitindo que
	 * o assistente acesse informações detalhadas de pedidos com
	 * validação de segurança.
	 * 
	 * A ferramenta:
	 * 1. Valida a identidade do usuário (nome e sobrenome)
	 * 2. Busca o pedido específico
	 * 3. Retorna informações completas se validado
	 * 4. Retorna null se o pedido não for encontrado ou não pertencer ao usuário
	 * 
	 * Informações retornadas:
	 * - ID do pedido e do usuário
	 * - Nome completo do usuário
	 * - Lista de produtos
	 * - Status atual
	 * - Valor total
	 * - Data de criação
	 * 
	 * @param pedidoId identificador único do pedido
	 * @param primeiroNome primeiro nome do usuário para validação
	 * @param ultimoNome último nome do usuário para validação
	 * @return detalhes completos do pedido ou null se não encontrado/autorizado
	 */
	@Tool
	public DetalhesPedido obterDetalhesPedidoPorIdEUsuario(Long pedidoId, String primeiroNome, String ultimoNome) {
		return pedidoService.obterDetalhesPedidoPorIdEUsuario(pedidoId, primeiroNome, ultimoNome);
	}

	/**
	 * Ferramenta para cancelar um pedido.
	 * 
	 * Esta ferramenta permite que o assistente execute cancelamentos
	 * de pedidos com validação de segurança. É uma operação crítica
	 * que requer confirmação de identidade.
	 * 
	 * Processo de cancelamento:
	 * 1. Valida a identidade do usuário
	 * 2. Localiza o pedido específico
	 * 3. Altera o status para CANCELADO
	 * 4. Persiste a alteração no banco
	 * 5. Retorna os detalhes atualizados
	 * 
	 * Regras de negócio aplicadas:
	 * - Só cancela pedidos do próprio usuário
	 * - Mantém histórico completo após cancelamento
	 * - Não valida se cancelamento é permitido (pode ser estendido)
	 * 
	 * @param pedidoId identificador único do pedido
	 * @param primeiroNome primeiro nome do usuário para validação
	 * @param ultimoNome último nome do usuário para validação
	 * @return detalhes do pedido cancelado ou null se não encontrado/autorizado
	 */
	@Tool
	public DetalhesPedido cancelarPedido(Long pedidoId, String primeiroNome, String ultimoNome) {
		return pedidoService.cancelarPedido(pedidoId, primeiroNome, ultimoNome);
	}
}