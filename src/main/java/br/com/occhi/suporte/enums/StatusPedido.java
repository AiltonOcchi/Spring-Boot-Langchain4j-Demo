package br.com.occhi.suporte.enums;

/**
 * Enumeração que define os possíveis status de um pedido no sistema.
 * 
 * Esta enum é utilizada para controlar o ciclo de vida de um pedido,
 * desde sua criação até sua finalização ou cancelamento.
 * 
 * Os status seguem uma sequência lógica de estados que um pedido
 * pode assumir durante seu processamento.
 * 
 * @author Ailton Occhi
 * @version 1.0
 * @since 2025
 */
public enum StatusPedido {
	
	/**
	 * Status inicial de um pedido recém-criado.
	 * 
	 * Indica que o pedido foi registrado no sistema mas ainda
	 * não iniciou o processamento. Normalmente, pedidos neste
	 * status aguardam confirmação de pagamento ou validação.
	 */
	NOVO,
	
	/**
	 * Status que indica que o pedido está sendo processado.
	 * 
	 * O pedido passou pelas validações iniciais e está em
	 * alguma etapa do processo de fulfillment (separação,
	 * embalagem, preparação para envio, etc.).
	 */
	EM_ANDAMENTO,
	
	/**
	 * Status que indica que o pedido foi finalizado com sucesso.
	 * 
	 * O pedido foi completamente processado e entregue ao cliente.
	 * Este é um status final positivo do ciclo de vida do pedido.
	 */
	CONCLUIDO,
	
	/**
	 * Status que indica que o pedido foi cancelado.
	 * 
	 * O pedido foi cancelado por solicitação do cliente, por
	 * problemas no processamento, ou por outras razões operacionais.
	 * Este é um status final que interrompe o ciclo normal do pedido.
	 */
	CANCELADO
}
