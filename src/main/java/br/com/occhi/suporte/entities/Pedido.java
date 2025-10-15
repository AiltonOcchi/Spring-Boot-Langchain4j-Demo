package br.com.occhi.suporte.entities;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.com.occhi.suporte.enums.StatusPedido;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidade JPA que representa um pedido no sistema de vendas.
 * 
 * Esta classe mapeia a tabela "pedidos" no banco de dados e contém
 * todas as informações relacionadas a um pedido realizado por um usuário,
 * incluindo os produtos, status, valor total e dados de auditoria.
 * 
 * Relacionamentos:
 * - ManyToOne com Usuario: cada pedido pertence a um usuário
 * - ManyToMany com Produto: um pedido pode conter múltiplos produtos
 * 
 * A entidade utiliza Lombok para geração automática de getters, setters,
 * toString, equals e hashCode.
 * 
 * @author Ailton Occhi
 * @version 1.0
 * @since 2025
 */
@Entity
@ToString
@EqualsAndHashCode
@Table(name = "pedidos")
public class Pedido {

	/**
	 * Construtor padrão necessário para o JPA.
	 */
	public Pedido() {}
	
	/**
	 * Identificador único do pedido.
	 * Chave primária gerada automaticamente usando estratégia IDENTITY.
	 */
	@Id
	@Getter @Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pedido_id")
	private Long pedidoId;

	/**
	 * Usuário proprietário do pedido.
	 * 
	 * Relacionamento Many-to-One: vários pedidos podem pertencer
	 * ao mesmo usuário, mas cada pedido pertence a apenas um usuário.
	 * Campo obrigatório (nullable = false).
	 */
	@Getter @Setter
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

	/**
	 * Data e hora de criação do pedido.
	 * 
	 * Utilizado para auditoria e controle temporal dos pedidos.
	 * Deve ser preenchido automaticamente no momento da criação.
	 */
	@Getter @Setter
	@Column(name = "criado_em")
	private LocalDateTime criadoEm;

	/**
	 * Lista de produtos incluídos no pedido.
	 * 
	 * Relacionamento Many-to-Many: um pedido pode conter múltiplos produtos
	 * e um produto pode estar em múltiplos pedidos.
	 * 
	 * A tabela intermediária "pedidos_produtos" é criada automaticamente
	 * para mapear essa relação.
	 */
	@Getter @Setter
	@ManyToMany
	@JoinTable(
			name = "pedidos_produtos",
			joinColumns = @JoinColumn(name = "pedido_id"),
			inverseJoinColumns = @JoinColumn(name = "produto_id")
	)
	private List<Produto> produtos;

	/**
	 * Status atual do pedido.
	 * 
	 * Utiliza enum StatusPedido para garantir que apenas valores
	 * válidos sejam armazenados (NOVO, EM_ANDAMENTO, CONCLUIDO, CANCELADO).
	 * 
	 * Armazenado como STRING no banco de dados para facilitar legibilidade.
	 */
	@Getter @Setter
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusPedido status;

	/**
	 * Valor total do pedido.
	 * 
	 * Utiliza BigDecimal para precisão em cálculos monetários.
	 * Representa a soma dos preços de todos os produtos do pedido.
	 */
	@Getter @Setter
	@Column(name = "valor_total")
	private BigDecimal valorTotal;

}