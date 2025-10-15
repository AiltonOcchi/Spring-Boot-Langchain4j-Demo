package br.com.occhi.suporte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot para o sistema "Suporte Vendas".
 * 
 * Esta aplicação implementa um sistema de suporte ao cliente integrado com
 * inteligência artificial usando LangChain4j. O sistema permite:
 * - Gerenciamento de pedidos, produtos e usuários
 * - Assistente virtual para suporte ao cliente
 * - API REST para consultas e operações sobre pedidos
 * 
 * @author Ailton Occhi
 * @version 1.0
 * @since 2025
 */
@SpringBootApplication
public class SuporteVendasApplication {

	/**
	 * Método principal que inicializa a aplicação Spring Boot.
	 * 
	 * Este método é responsável por:
	 * - Configurar e inicializar o contexto da aplicação Spring
	 * - Carregar todas as configurações definidas em application.properties
	 * - Inicializar os beans necessários para o funcionamento da aplicação
	 * - Subir o servidor web na porta configurada (padrão: 8080)
	 * 
	 * @param args argumentos da linha de comando passados para a aplicação
	 */
	public static void main(String[] args) {
		SpringApplication.run( SuporteVendasApplication.class, args);
	}

}
