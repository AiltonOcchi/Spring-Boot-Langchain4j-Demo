# 🤖 Projeto de IA Corporativa com Java e LangChain4j

## 💡 Sobre o Projeto
Este projeto foi desenvolvido para **mostrar na prática** como implementar **soluções reais de Inteligência Artificial Corporativa** utilizando **Java**, **Spring Boot** e **LangChain4j**.

O sistema **executa uma implementação funcional completa**, simulando um **ambiente de suporte ao cliente com um assistente virtual inteligente** servindo como base de aprendizado para integração de IA em aplicações empresariais.


## 🎯 Objetivo do Projeto

Criar uma **base sólida de aprendizado** para desenvolvedores e equipes que desejam **integrar recursos de IA em aplicações empresariais**, explorando desde a arquitetura até a integração com modelos de linguagem, demonstrando:
- **Integração de IA em aplicações Java corporativas**
- **Uso prático do LangChain4j** para conectar aplicações Java com LLMs
- **Implementação de assistentes virtuais** com contexto empresarial
- **Gestão de memória conversacional** em chatbots corporativos
- **Criação de ferramentas customizadas** para IA acessar dados empresariais
- **Boas práticas de segurança** em sistemas de IA corporativa

## 💡 Cenário de Demonstração

O sistema implementa um **assistente virtual chamado "Robozinho"** para o sistema de vendas fictício **"Venda Fácil"**, demonstrando como uma IA pode:
- Consultar dados empresariais de forma natural
- Manter contexto de conversação
- Executar operações de negócio com validações de segurança
- Integrar-se com sistemas legados via APIs

## 🚀 Funcionalidades

- **Assistente Virtual Inteligente**: IA conversacional (GPT-4o-mini) que responde perguntas sobre pedidos
- **Gestão de Pedidos**: Consulta, cancelamento e obtenção de detalhes de pedidos
- **Memória Contextual**: Mantém contexto de conversação por sessão de usuário
- **Validação de Segurança**: Exige autenticação do usuário antes de operações sensíveis
- **Interface REST**: APIs para interação direta e via assistente IA
- **Base de Dados Completa**: Sistema com usuários, produtos e pedidos pré-populados

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.6**
- **LangChain4j 0.36.2** - Integração com IA
- **OpenAI GPT-4o-mini** - Modelo de linguagem
- **PostgreSQL** - Banco de dados
- **JPA/Hibernate** - ORM
- **Maven** - Gerenciamento de dependências

## 📋 Pré-requisitos

- Java 21+
- PostgreSQL 12+
- Maven 3.6+
- Chave de API OpenAI (opcional - configurado para usar "demo" por padrão)

## ⚙️ Configuração

### 1. Configuração do Banco de Dados

1. Crie o banco PostgreSQL:
```sql
CREATE DATABASE suporteVendas;
```

2. Execute os scripts SQL em `src/main/resources/static/db/database.sql` para:
   - Criar as tabelas (usuarios, produtos, pedidos, pedidos_produtos)
   - Inserir dados de exemplo (20 usuários, 50 produtos, 30 pedidos)

### 2. Configuração da Aplicação

Edite o arquivo `src/main/resources/application.properties`:

```properties
# Configuração do Banco (ajuste conforme seu ambiente)
spring.datasource.url=jdbc:postgresql://localhost:5432/suporte-vendas
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

# Configuração OpenAI (opcional - substitua por sua chave real)
langchain4j.open-ai.chat-model.api-key=sua_chave_api_openai
```

**Nota**: O projeto vem configurado com uma chave de demonstração (`demo`) que permite testar a funcionalidade.

## 🚀 Execução

### Via Maven
```bash
mvn spring-boot:run
```

### Via JAR
```bash
mvn clean package
java -jar target/suporteVendas-0.0.1-SNAPSHOT.jar
```

A aplicação estará disponível em `http://localhost:8080`

## 📡 Endpoints da API 

### Assistente IA
```http
GET /assistente/chat?sessionId={id}&message={mensagem}
```
**Exemplo:**
```
GET /assistente/chat?sessionId=user123&message=Quantos pedidos o usuário 1000 tem?
```

### APIs Diretas de Pedidos
```http
GET /pedidos/usuario/{usuarioId}/quantidade
GET /pedidos/{pedidoId}/detalhes
GET /pedidos/status/{status}
DELETE /pedidos/{pedidoId}/cancelar?primeiroNome={nome}&ultimoNome={sobrenome}
```

## 🤖 Como Usar o Assistente

### Funcionalidades Disponíveis

1. **Consultar quantidade de pedidos de um usuário**
   ```
   "Quantos pedidos o usuário 1000 tem?"
   ```

2. **Obter detalhes de um pedido específico**
   ```
   "Preciso dos detalhes do pedido 3000. Meu nome é Ana Silva."
   ```

3. **Cancelar um pedido**
   ```
   "Quero cancelar meu pedido 3001. Sou Ana Silva."
   ```

4. **Buscar pedidos por status**
   ```
   "Mostre todos os pedidos em andamento"
   ```

5. **Encontrar o pedido mais caro**
   ```
   "Qual é o pedido mais caro?"
   ```

### Regras de Segurança

- Para obter detalhes ou cancelar pedidos, o assistente exige: **nome**, **sobrenome** e **ID do pedido**
- O assistente só responde perguntas relacionadas ao sistema de pedidos
- Cada `sessionId` mantém uma conversa independente

## 📊 Dados de Exemplo

O sistema vem pré-carregado com:
- **20 usuários** (IDs 1000-1019)
- **50 produtos** tecnológicos (IDs 2000-2049) 
- **30 pedidos** com diferentes status (IDs 3000-3029)

### Usuários de Teste
- Ana Silva (ID: 1000) - 3 pedidos
- Carlos Santos (ID: 1001) - 3 pedidos  
- Maria Oliveira (ID: 1002) - 1 pedido

### Status dos Pedidos
- `NOVO` - Pedido criado
- `EM_ANDAMENTO` - Pedido sendo processado
- `CONCLUIDO` - Pedido finalizado
- `CANCELADO` - Pedido cancelado

## 🏗️ Arquitetura do Sistema

```
├── controllers/          # Endpoints REST
│   ├── AssistenteSuporteVendasController  # Chat com IA
│   └── PedidoController                   # CRUD de pedidos
├── services/             # Lógica de negócio
│   ├── AssistenteSuporteVendas           # Interface IA
│   ├── PedidoService                     # Serviços de pedido
│   └── PedidoTool                        # Ferramentas para IA
├── entities/             # Modelos JPA
│   ├── Usuario, Produto, Pedido
├── repositories/         # Acesso a dados
└── config/               # Configuração IA
    └── AgentConfiguration # Memory provider
```

## 🔧 Personalização

### Modificar Comportamento do Assistente

Edite a `@SystemMessage` em `AssistenteSuporteVendas.java`:

```java
@SystemMessage("""
    Seu nome é Robozinho e você é assistente de suporte ao cliente...
    // Adicione suas regras personalizadas aqui
""")
```

### Adicionar Novas Toools (Ferramentas)

Crie novos métodos com `@Tool` em `PedidoTool.java`:

```java
@Tool
public String minhaNovaFerramenta(String parametro) {
    // Sua lógica aqui
    return "Resultado";
}
```

## 📝 Logs e Debug

O sistema está configurado para log detalhado:
- Requisições e respostas da OpenAI são logadas
- Nível DEBUG ativado para LangChain4j
- Logs disponíveis no console da aplicação

## 🔒 Considerações de Segurança

- Validação obrigatória de identidade para operações sensíveis
- Escopo restrito do assistente apenas ao domínio de pedidos
- Sanitização automática de entradas via LangChain4j

## 🤝 Contribuição

Contribuições são bem-vindas!  
Siga as etapas abaixo para colaborar com este projeto:

1. Faça um **fork** do projeto  
2. Crie uma nova **branch** para sua feature (`git checkout -b feature/nome-da-feature`)  
3. **Commit** suas alterações (`git commit -m 'Adiciona nova feature'`)  
4. Faça o **push** da branch (`git push origin feature/nome-da-feature`)  
5. Abra um **Pull Request**

## 📄 Licença


Este projeto foi desenvolvido com fins **educacionais** e **demonstrativos**, utilizando **Spring Boot** e **LangChain4j**.  
Sinta-se à vontade para explorar, estudar e adaptar o código conforme suas necessidades.


## 👨‍💻 Autor
**Desenvolvido por [Ailton Occhi](https://github.com/ailtonocchi)**  
💼 Projeto de exemplo de **integração de IA com Spring Boot e LangChain4j**.