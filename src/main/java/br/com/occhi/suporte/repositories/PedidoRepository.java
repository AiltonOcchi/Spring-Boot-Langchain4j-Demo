package br.com.occhi.suporte.repositories;
import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.occhi.suporte.entities.Pedido;
import br.com.occhi.suporte.enums.StatusPedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	@Query("SELECT COUNT(p) FROM Pedido p WHERE p.usuario.usuarioId = :usuarioId")
	Integer buscarQuantidadePedidosPorUsuario(Long usuarioId);

	@Query("SELECT COUNT(p) FROM Pedido p WHERE p.status = :status")
	Integer buscarQuantidadePedidosPorStatus(StatusPedido status);

	@Query("SELECT MAX(p.valorTotal) FROM Pedido p")
	BigDecimal buscarValorPedidoMaisCaro();

	@Query("SELECT p FROM Pedido p WHERE p.pedidoId = :pedidoId AND p.usuario.primeiroNome = :primeiroNome AND p.usuario.ultimoNome = :ultimoNome")
	Pedido buscarDetalhesPedidoPorIdEUsuario(Long pedidoId, String primeiroNome, String ultimoNome);
}