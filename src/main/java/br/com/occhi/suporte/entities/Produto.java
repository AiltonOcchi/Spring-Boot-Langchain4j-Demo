package br.com.occhi.suporte.entities;
import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidade JPA que representa um produto no sistema de vendas.
 * 
 * Esta classe mapeia a tabela "produtos" no banco de dados e contém
 * as informações básicas de produtos que podem ser incluídos em pedidos.
 * 
 * Relacionamentos:
 * - ManyToMany com Pedido: um produto pode estar em múltiplos pedidos
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
@Table(name = "produtos")
public class Produto {

	/**
	 * Construtor padrão necessário para o JPA.
	 */
	public Produto() {}

	/**
	 * Identificador único do produto.
	 * Chave primária gerada automaticamente usando estratégia IDENTITY.
	 */
	@Id
	@Getter @Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "produto_id")
	private Long produtoId;

	/**
	 * Nome do produto.
	 * 
	 * Campo obrigatório que identifica o produto de forma clara
	 * e compreensível para usuários e sistema.
	 */
	@Getter @Setter
	@Column(name = "nome")
	private String nome;

	/**
	 * Descrição detalhada do produto.
	 * 
	 * Campo opcional que fornece informações adicionais sobre
	 * o produto, como características, especificações técnicas,
	 * instruções de uso, etc.
	 */
	@Getter @Setter
	@Column(name = "descricao")
	private String descricao;

	/**
	 * Preço unitário do produto.
	 * 
	 * Utiliza BigDecimal para garantir precisão em cálculos monetários.
	 * Configurado com precisão de 10 dígitos e 2 casas decimais.
	 * 
	 * Exemplo: 199.99 para R$ 199,99
	 */
	@Getter @Setter
	@Column(name = "preco", precision = 10, scale = 2)
	private BigDecimal preco;

	/**
	 * Lista de pedidos que contêm este produto.
	 * 
	 * Relacionamento Many-to-Many bidirecional com a entidade Pedido.
	 * O mapeamento é definido pelo atributo "produtos" na classe Pedido.
	 * 
	 * Este campo permite navegar do produto para os pedidos que o contêm,
	 * útil para relatórios de vendas e análises de popularidade do produto.
	 */
	@Getter @Setter
	@ManyToMany(mappedBy = "produtos")
	private List<Pedido> pedidos;

}