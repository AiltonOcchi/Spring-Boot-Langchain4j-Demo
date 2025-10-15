package br.com.occhi.suporte.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.occhi.suporte.records.DetalhesPedido;
import br.com.occhi.suporte.services.PedidoService;

/**
 * Controller REST para operações relacionadas a pedidos.
 * 
 * Esta classe expõe endpoints HTTP para consultas e operações sobre pedidos,
 * permitindo que sistemas externos ou o frontend da aplicação obtenham
 * informações sobre pedidos dos usuários.
 * 
 * Funcionalidades disponíveis:
 * - Consultar quantidade de pedidos por usuário
 * - Obter detalhes completos de um pedido específico
 * - Validação de acesso baseada em dados do usuário
 * 
 * @author Ailton Occhi
 * @version 1.0
 * @since 2025
 */
@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
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
	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	/**
	 * Busca a quantidade total de pedidos de um usuário específico.
	 * 
	 * Este endpoint retorna o número total de pedidos realizados por um usuário,
	 * independentemente do status dos pedidos (ativos, cancelados, concluídos).
	 * 
	 * Exemplo de uso:
	 * - GET /pedidos/?usuarioId=123
	 * 
	 * @param usuarioId ID único do usuário no sistema
	 * @return ResponseEntity contendo a quantidade de pedidos do usuário
	 */
	@GetMapping("/")
	public ResponseEntity<Integer> buscarPedidosPorUsuario(@RequestParam Long usuarioId){
		return ResponseEntity.ok(pedidoService.obterQuantidadePedidosPorUsuario(usuarioId));
	}

	/**
	 * Busca os detalhes completos de um pedido específico.
	 * 
	 * Este endpoint retorna informações detalhadas de um pedido, incluindo:
	 * - Dados do usuário (nome e sobrenome)
	 * - Lista de produtos do pedido
	 * - Status atual do pedido
	 * - Valor total e data de criação
	 * 
	 * A consulta inclui validação de segurança, exigindo que o nome e sobrenome
	 * do usuário sejam fornecidos para confirmar a propriedade do pedido.
	 * 
	 * Exemplo de uso:
	 * - GET /pedidos/123?primeiroNome=João&ultimoNome=Silva
	 * 
	 * @param pedidoId ID único do pedido no sistema
	 * @param primeiroNome primeiro nome do usuário proprietário do pedido
	 * @param ultimoNome último nome do usuário proprietário do pedido
	 * @return ResponseEntity contendo os detalhes completos do pedido
	 */
	@GetMapping("/{pedidoId}")
	public ResponseEntity<DetalhesPedido> buscarDetalhesPedido(@PathVariable Long pedidoId, @RequestParam String primeiroNome, @RequestParam String ultimoNome){
		return ResponseEntity.ok(pedidoService.obterDetalhesPedidoPorIdEUsuario(pedidoId, primeiroNome, ultimoNome));
	}
}