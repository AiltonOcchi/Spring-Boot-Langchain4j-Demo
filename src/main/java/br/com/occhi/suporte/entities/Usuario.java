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

/**
 * Entidade JPA que representa um usuário no sistema de vendas.
 * 
 * Esta classe mapeia a tabela "usuarios" no banco de dados e contém
 * as informações básicas dos usuários que podem realizar pedidos no sistema.
 * 
 * Relacionamentos:
 * - OneToMany com Pedido: um usuário pode ter múltiplos pedidos
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
@Table(name = "usuarios")
public class Usuario {

	/**
	 * Construtor padrão necessário para o JPA.
	 */
	public Usuario() {}

	/**
	 * Identificador único do usuário.
	 * Chave primária gerada automaticamente usando estratégia IDENTITY.
	 */
	@Id
	@Getter @Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usuario_id")
	private Long usuarioId;

	/**
	 * Primeiro nome do usuário.
	 * 
	 * Campo obrigatório usado para identificação pessoal e
	 * validação de acesso aos pedidos no sistema de suporte.
	 */
	@Getter @Setter
	@Column(name = "primeiro_nome")
	private String primeiroNome;

	/**
	 * Último nome (sobrenome) do usuário.
	 * 
	 * Campo obrigatório usado em conjunto com o primeiro nome
	 * para validação de identidade e acesso aos pedidos.
	 */
	@Getter @Setter
	@Column(name = "ultimo_nome")
	private String ultimoNome;

	/**
	 * Endereço de email do usuário.
	 * 
	 * Campo único usado para comunicação e possível autenticação.
	 * Deve seguir formato padrão de email (exemplo@dominio.com).
	 */
	@Getter @Setter
	@Column(name = "email")
	private String email;

	/**
	 * Lista de pedidos realizados pelo usuário.
	 * 
	 * Relacionamento One-to-Many bidirecional com a entidade Pedido.
	 * 
	 * Configurações:
	 * - CascadeType.ALL: operações realizadas no usuário são propagadas aos pedidos
	 * - orphanRemoval = true: pedidos órfãos são automaticamente removidos
	 * - mappedBy = "usuario": indica que o mapeamento é feito pelo campo "usuario" na entidade Pedido
	 * 
	 * Isso significa que ao remover um usuário, todos os seus pedidos também serão removidos.
	 */
	@Getter @Setter
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Pedido> pedidos;

}