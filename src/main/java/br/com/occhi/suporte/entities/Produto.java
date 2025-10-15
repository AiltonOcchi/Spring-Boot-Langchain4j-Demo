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

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "produtos")
public class Produto {

	public Produto() {}

	@Id
	@Getter @Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "produto_id")
	private Long produtoId;

	@Getter @Setter
	@Column(name = "nome")
	private String nome;

	@Getter @Setter
	@Column(name = "descricao")
	private String descricao;

	@Getter @Setter
	@Column(name = "preco", precision = 10, scale = 2)
	private BigDecimal preco;

	@Getter @Setter
	@ManyToMany(mappedBy = "produtos")
	private List<Pedido> pedidos;


}