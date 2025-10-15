package br.com.occhi.suporte.services;
import java.math.BigDecimal;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import br.com.occhi.suporte.enums.StatusPedido;
import br.com.occhi.suporte.records.DetalhesPedido;

@Component
public class PedidoTool {
	private final PedidoService pedidoService;

	public PedidoTool(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	@Tool
	public Integer obterQuantidadePedidosPorUsuario(Long usuarioId) {
		return pedidoService.obterQuantidadePedidosPorUsuario(usuarioId);
	}

	@Tool
	public int obterQuantidadePedidosPorStatus(StatusPedido status) {
		return pedidoService.obterQuantidadePedidosPorStatus(status);
	}

	@Tool
	public BigDecimal obterValorPedidoMaisCaro() {
		return pedidoService.obterValorPedidoMaisCaro();
	}

	@Tool
	public DetalhesPedido obterDetalhesPedidoPorIdEUsuario(Long pedidoId, String primeiroNome, String ultimoNome) {
		return pedidoService.obterDetalhesPedidoPorIdEUsuario(pedidoId, primeiroNome, ultimoNome);
	}

	@Tool
	public DetalhesPedido cancelarPedido(Long pedidoId, String primeiroNome, String ultimoNome) {
		return pedidoService.cancelarPedido(pedidoId, primeiroNome, ultimoNome);
	}
}