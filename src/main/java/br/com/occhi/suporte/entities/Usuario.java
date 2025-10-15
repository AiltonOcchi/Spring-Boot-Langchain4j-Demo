package br.com.occhi.suporte.entities;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "usuarios")
public class Usuario {

	public Usuario() {}

	@Id
	@Getter @Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usuario_id")
	private Long usuarioId;

	@Getter @Setter
	@Column(name = "primeiro_nome")
	private String primeiroNome;

	@Getter @Setter
	@Column(name = "ultimo_nome")
	private String ultimoNome;

	@Getter @Setter
	@Column(name = "email")
	private String email;

	@Getter @Setter
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Pedido> pedidos;

}