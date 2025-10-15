package br.com.occhi.suporte.repositories;
import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.occhi.suporte.entities.Pedido;
import br.com.occhi.suporte.enums.StatusPedido;

/**
 * Repositório JPA para operações de acesso a dados da entidade Pedido.
 * 
 * Esta interface estende JpaRepository, fornecendo operações CRUD básicas
 * e define consultas customizadas específicas para o domínio de pedidos.
 * 
 * O Spring Data JPA implementa automaticamente esta interface, criando
 * um bean gerenciado que pode ser injetado em outros componentes.
 * 
 * Funcionalidades disponíveis:
 * - Operações CRUD herdadas do JpaRepository
 * - Consultas customizadas usando @Query com JPQL
 * - Métodos de contagem e agregação
 * - Consultas com validação de segurança
 * 
 * @author Ailton Occhi
 * @version 1.0
 * @since 2025
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
	/**
	 * Busca a quantidade total de pedidos de um usuário específico.
	 * 
	 * Esta consulta conta todos os pedidos associados a um usuário,
	 * independentemente do status (ativo, cancelado, concluído).
	 * 
	 * Útil para:
	 * - Exibir estatísticas do usuário
	 * - Validar histórico de compras
	 * - Análises de comportamento do cliente
	 * 
	 * @param usuarioId identificador único do usuário
	 * @return quantidade total de pedidos do usuário
	 */
	@Query("SELECT COUNT(p) FROM Pedido p WHERE p.usuario.usuarioId = :usuarioId")
	Integer buscarQuantidadePedidosPorUsuario(Long usuarioId);

	/**
	 * Busca a quantidade de pedidos com um status específico.
	 * 
	 * Esta consulta permite análises operacionais sobre o estado
	 * geral dos pedidos no sistema, como:
	 * - Quantos pedidos estão pendentes (NOVO)
	 * - Quantos estão sendo processados (EM_ANDAMENTO)
	 * - Quantos foram finalizados (CONCLUIDO)
	 * - Quantos foram cancelados (CANCELADO)
	 * 
	 * @param status status do pedido para filtrar a contagem
	 * @return quantidade de pedidos com o status especificado
	 */
	@Query("SELECT COUNT(p) FROM Pedido p WHERE p.status = :status")
	Integer buscarQuantidadePedidosPorStatus(StatusPedido status);

	/**
	 * Busca o maior valor total entre todos os pedidos.
	 * 
	 * Esta consulta retorna o valor do pedido mais caro já registrado
	 * no sistema, útil para:
	 * - Análises de vendas e estatísticas
	 * - Relatórios gerenciais
	 * - Identificação de padrões de compra
	 * 
	 * @return valor total do pedido mais caro, ou null se não houver pedidos
	 */
	@Query("SELECT MAX(p.valorTotal) FROM Pedido p")
	BigDecimal buscarValorPedidoMaisCaro();

	/**
	 * Busca os detalhes de um pedido específico com validação de usuário.
	 * 
	 * Esta consulta implementa uma camada de segurança, exigindo que
	 * o primeiro nome e último nome do usuário sejam fornecidos para
	 * confirmar a propriedade do pedido.
	 * 
	 * Casos de uso:
	 * - Suporte ao cliente verificando identidade
	 * - Usuários consultando seus próprios pedidos
	 * - APIs que requerem validação de acesso
	 * 
	 * Retorna null se:
	 * - O pedido não existir
	 * - O pedido não pertencer ao usuário especificado
	 * - O nome fornecido não coincidir com o proprietário
	 * 
	 * @param pedidoId identificador único do pedido
	 * @param primeiroNome primeiro nome do usuário para validação
	 * @param ultimoNome último nome do usuário para validação
	 * @return entidade Pedido completa se as validações passarem, null caso contrário
	 */
	@Query("SELECT p FROM Pedido p WHERE p.pedidoId = :pedidoId AND p.usuario.primeiroNome = :primeiroNome AND p.usuario.ultimoNome = :ultimoNome")
	Pedido buscarDetalhesPedidoPorIdEUsuario(Long pedidoId, String primeiroNome, String ultimoNome);
}