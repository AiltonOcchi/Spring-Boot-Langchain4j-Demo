package br.com.occhi.suporte.records;
import java.math.BigDecimal;
import java.util.List;

import br.com.occhi.suporte.enums.StatusPedido;

/**
 * Record que representa os detalhes completos de um pedido.
 * 
 * Esta classe é uma estrutura de dados imutável (Java Record) utilizada
 * para transferir informações detalhadas de um pedido entre as camadas
 * da aplicação, especialmente como resposta das APIs REST.
 * 
 * O record encapsula todas as informações relevantes de um pedido
 * em uma única estrutura, facilitando a serialização para JSON
 * e a manipulação pelos clientes da API.
 * 
 * Características dos Records Java:
 * - Imutáveis por natureza
 * - Métodos equals(), hashCode() e toString() gerados automaticamente
 * - Construtores e getters gerados automaticamente
 * - Ideais para DTOs (Data Transfer Objects)
 * 
 * @author Ailton Occhi
 * @version 1.0
 * @since 2025
 * 
 * @param pedidoId identificador único do pedido
 * @param usuarioId identificador único do usuário proprietário do pedido
 * @param primeiroNome primeiro nome do usuário
 * @param ultimoNome último nome do usuário
 * @param nomesProdutos lista com os nomes de todos os produtos incluídos no pedido
 * @param status status atual do pedido (NOVO, EM_ANDAMENTO, CONCLUIDO, CANCELADO)
 * @param valorTotal valor monetário total do pedido
 * @param criadoEm data e hora de criação do pedido em formato string
 */
public record DetalhesPedido(
		Long pedidoId, 
		Long usuarioId, 
		String primeiroNome, 
		String ultimoNome, 
		List<String> nomesProdutos, 
		StatusPedido status, 
		BigDecimal valorTotal, 
		String criadoEm
) {}