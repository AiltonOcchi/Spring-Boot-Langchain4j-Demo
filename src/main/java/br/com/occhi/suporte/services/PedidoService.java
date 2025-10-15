package br.com.occhi.suporte.services;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import br.com.occhi.suporte.entities.Pedido;
import br.com.occhi.suporte.entities.Produto;
import br.com.occhi.suporte.enums.StatusPedido;
import br.com.occhi.suporte.records.DetalhesPedido;
import br.com.occhi.suporte.repositories.PedidoRepository;

/**
 * Serviço responsável pela lógica de negócio relacionada a pedidos.
 * 
 * Esta classe implementa as regras de negócio e operações relacionadas
 * aos pedidos no sistema, atuando como uma camada intermediária entre
 * os controllers e o repositório de dados.
 * 
 * Responsabilidades:
 * - Implementar regras de negócio para operações com pedidos
 * - Coordenar chamadas ao repositório
 * - Transformar entidades em DTOs quando necessário
 * - Validar dados e aplicar lógica de domínio
 * - Gerenciar operações transacionais
 * 
 * @author Ailton Occhi
 * @version 1.0
 * @since 2025
 */
@Service
public class PedidoService {
	
	/**
	 * Repositório de pedidos para acesso aos dados.
	 * Injetado automaticamente pelo Spring Boot.
	 */
	private final PedidoRepository pedidoRepository;

	/**
	 * Construtor para injeção de dependência.
	 * 
	 * @param pedidoRepository repositório de pedidos
	 */
	public PedidoService(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}

	/**
	 * Obtém a quantidade total de pedidos de um usuário específico.
	 * 
	 * Este método é útil para:
	 * - Exibir estatísticas do usuário
	 * - Validar histórico de compras
	 * - Análises de comportamento do cliente
	 * 
	 * @param usuarioId identificador único do usuário
	 * @return quantidade total de pedidos do usuário
	 */
	public Integer obterQuantidadePedidosPorUsuario(Long usuarioId) {
		return pedidoRepository.buscarQuantidadePedidosPorUsuario(usuarioId);
	}

	/**
	 * Obtém a quantidade de pedidos com um status específico.
	 * 
	 * Este método permite análises operacionais sobre o estado
	 * dos pedidos no sistema, como pedidos pendentes, em andamento,
	 * concluídos ou cancelados.
	 * 
	 * @param status status do pedido para filtrar a contagem
	 * @return quantidade de pedidos com o status especificado
	 */
	public Integer obterQuantidadePedidosPorStatus(StatusPedido status) {
		return pedidoRepository.buscarQuantidadePedidosPorStatus(status);
	}

	/**
	 * Obtém o valor do pedido mais caro registrado no sistema.
	 * 
	 * Útil para análises de vendas, relatórios gerenciais e
	 * identificação de padrões de compra.
	 * 
	 * @return valor total do pedido mais caro, ou null se não houver pedidos
	 */
	public BigDecimal obterValorPedidoMaisCaro() {
		return pedidoRepository.buscarValorPedidoMaisCaro();
	}

	/**
	 * Obtém os detalhes completos de um pedido com validação de usuário.
	 * 
	 * Este método implementa uma camada de segurança, exigindo que
	 * o primeiro nome e último nome do usuário sejam fornecidos para
	 * confirmar a propriedade do pedido.
	 * 
	 * O método:
	 * 1. Busca o pedido usando validação de identidade
	 * 2. Converte a entidade Pedido em um DTO DetalhesPedido
	 * 3. Extrai os nomes dos produtos para simplificar a resposta
	 * 4. Formata a data de criação como string
	 * 
	 * @param pedidoId identificador único do pedido
	 * @param primeiroNome primeiro nome do usuário para validação
	 * @param ultimoNome último nome do usuário para validação
	 * @return DetalhesPedido com informações completas, ou null se não encontrado
	 */
	public DetalhesPedido obterDetalhesPedidoPorIdEUsuario(Long pedidoId, String primeiroNome, String ultimoNome) {
		Pedido pedido = pedidoRepository.buscarDetalhesPedidoPorIdEUsuario(pedidoId, primeiroNome, ultimoNome);

		if(pedido == null){
			return null;
		}

		return new DetalhesPedido(
				pedido.getPedidoId(),
				pedido.getUsuario().getUsuarioId(),
				pedido.getUsuario().getPrimeiroNome(),
				pedido.getUsuario().getUltimoNome(),
				pedido.getProdutos().stream().map( Produto::getNome ).toList(),
				pedido.getStatus(),
				pedido.getValorTotal(),
				pedido.getCriadoEm().toString());
	}

	/**
	 * Cancela um pedido específico com validação de usuário.
	 * 
	 * Este método implementa a funcionalidade de cancelamento de pedidos
	 * com as seguintes características:
	 * 
	 * 1. Validação de identidade: verifica nome e sobrenome do usuário
	 * 2. Alteração de status: muda o status para CANCELADO
	 * 3. Persistência: salva a alteração no banco de dados
	 * 4. Retorno de dados: retorna os detalhes atualizados do pedido
	 * 
	 * Regras de negócio:
	 * - Só permite cancelar pedidos que pertençam ao usuário especificado
	 * - Não valida se o pedido pode ser cancelado (ex: se já foi enviado)
	 * - Mantém histórico completo do pedido após cancelamento
	 * 
	 * @param pedidoId identificador único do pedido
	 * @param primeiroNome primeiro nome do usuário para validação
	 * @param ultimoNome último nome do usuário para validação
	 * @return DetalhesPedido com status atualizado, ou null se não encontrado
	 */
	public DetalhesPedido cancelarPedido(Long pedidoId, String primeiroNome, String ultimoNome) {
		Pedido pedido = pedidoRepository.buscarDetalhesPedidoPorIdEUsuario(pedidoId, primeiroNome, ultimoNome);

		if(pedido == null){
			return null;
		}

		pedido.setStatus(StatusPedido.CANCELADO);
		pedidoRepository.save(pedido);

		return new DetalhesPedido(
				pedido.getPedidoId(),
				pedido.getUsuario().getUsuarioId(),
				pedido.getUsuario().getPrimeiroNome(),
				pedido.getUsuario().getUltimoNome(),
				pedido.getProdutos().stream().map( Produto::getNome ).toList(),
				pedido.getStatus(),
				pedido.getValorTotal(),
				pedido.getCriadoEm().toString());
	}
}