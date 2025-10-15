package br.com.occhi.suporte.services;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import br.com.occhi.suporte.entities.Pedido;
import br.com.occhi.suporte.entities.Produto;
import br.com.occhi.suporte.enums.StatusPedido;
import br.com.occhi.suporte.records.DetalhesPedido;
import br.com.occhi.suporte.repositories.PedidoRepository;

@Service
public class PedidoService {
	private final PedidoRepository pedidoRepository;

	public PedidoService(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}

	public Integer obterQuantidadePedidosPorUsuario(Long usuarioId) {
		return pedidoRepository.buscarQuantidadePedidosPorUsuario(usuarioId);
	}

	public Integer obterQuantidadePedidosPorStatus(StatusPedido status) {
		return pedidoRepository.buscarQuantidadePedidosPorStatus(status);
	}

	public BigDecimal obterValorPedidoMaisCaro() {
		return pedidoRepository.buscarValorPedidoMaisCaro();
	}

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