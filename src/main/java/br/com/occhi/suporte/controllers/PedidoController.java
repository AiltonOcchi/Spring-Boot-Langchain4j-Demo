package br.com.occhi.suporte.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.occhi.suporte.records.DetalhesPedido;
import br.com.occhi.suporte.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	private final PedidoService pedidoService;

	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	@GetMapping("/")
	public ResponseEntity<Integer> buscarPedidosPorUsuario(@RequestParam Long usuarioId){
		return ResponseEntity.ok(pedidoService.obterQuantidadePedidosPorUsuario(usuarioId));
	}

	@GetMapping("/{pedidoId}")
	public ResponseEntity<DetalhesPedido> buscarDetalhesPedido(@PathVariable Long pedidoId, @RequestParam String primeiroNome, @RequestParam String ultimoNome){
		return ResponseEntity.ok(pedidoService.obterDetalhesPedidoPorIdEUsuario(pedidoId, primeiroNome, ultimoNome));
	}
}