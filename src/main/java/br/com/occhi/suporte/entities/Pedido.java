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

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "pedidos")
public class Pedido {

	public Pedido() {}
	
	@Id
	@Getter @Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pedido_id")
	private Long pedidoId;

	@Getter @Setter
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

	@Getter @Setter
	@Column(name = "criado_em")
	private LocalDateTime criadoEm;

	@Getter @Setter
	@ManyToMany
	@JoinTable(
			name = "pedidos_produtos",
			joinColumns = @JoinColumn(name = "pedido_id"),
			inverseJoinColumns = @JoinColumn(name = "produto_id")
	)
	private List<Produto> produtos;

	@Getter @Setter
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusPedido status;

	@Getter @Setter
	@Column(name = "valor_total")
	private BigDecimal valorTotal;

	

	
}