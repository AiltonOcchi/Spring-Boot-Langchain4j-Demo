package br.com.occhi.suporte.records;
import java.math.BigDecimal;
import java.util.List;

import br.com.occhi.suporte.enums.StatusPedido;

public record DetalhesPedido(Long pedidoId, Long usuarioId, String primeiroNome, String ultimoNome, List<String> nomesProdutos, StatusPedido status, BigDecimal valorTotal, String criadoEm) {
}