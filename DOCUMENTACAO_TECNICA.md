# Documentação Técnica - Sistema de Suporte de Vendas

## Visão Geral

O **Sistema de Suporte de Vendas** é uma aplicação Spring Boot que integra inteligência artificial para fornecer suporte automatizado ao cliente. O sistema utiliza LangChain4j para criar um assistente virtual capaz de responder perguntas sobre pedidos, fornecer informações sobre produtos e auxiliar com cancelamentos.

## Arquitetura da Aplicação

### Tecnologias Utilizadas

- **Java 17+** - Linguagem de programação
- **Spring Boot 3.x** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **PostgreSQL** - Banco de dados
- **LangChain4j** - Framework de IA
- **OpenAI GPT-4o-mini** - Modelo de linguagem
- **Lombok** - Redução de boilerplate
- **Maven** - Gerenciamento de dependências

### Estrutura de Pacotes

```
br.com.occhi.suporte/
├── config/          # Configurações da aplicação
├── controllers/     # Controllers REST
├── entities/        # Entidades JPA
├── enums/          # Enumerações
├── records/        # DTOs (Data Transfer Objects)
├── repositories/   # Repositórios de dados
└── services/       # Serviços e lógica de negócio
```

## Componentes da Aplicação

### 1. Camada de Configuração (`config/`)

#### `AgentConfiguration.java`
- Configura o provedor de memória para o assistente de IA
- Implementa janela de tokens para manter contexto das conversas
- Limite de 5000 tokens por sessão

### 2. Camada de Controle (`controllers/`)

#### `AssistenteSuporteVendasController.java`
- Endpoint: `GET /chat`
- Parâmetros: `sessionId`, `message`
- Responsável pela comunicação com o assistente de IA

#### `PedidoController.java`
- Endpoint: `GET /pedidos/` - Busca quantidade de pedidos por usuário
- Endpoint: `GET /pedidos/{pedidoId}` - Busca detalhes de pedido específico
- Implementa validação de segurança baseada em nome do usuário

### 3. Camada de Dados (`entities/`)

#### `Usuario.java`
- Entidade principal para usuários do sistema
- Relacionamento One-to-Many com Pedido
- Campos: ID, primeiro nome, último nome, email

#### `Pedido.java`
- Entidade central do sistema
- Relacionamentos: Many-to-One com Usuario, Many-to-Many com Produto
- Campos: ID, data criação, status, valor total
- Utiliza enum StatusPedido

#### `Produto.java`
- Entidade para produtos do catálogo
- Relacionamento Many-to-Many com Pedido
- Campos: ID, nome, descrição, preço

### 4. Enumerações (`enums/`)

#### `StatusPedido.java`
- Define ciclo de vida dos pedidos
- Valores: NOVO, EM_ANDAMENTO, CONCLUIDO, CANCELADO

### 5. DTOs (`records/`)

#### `DetalhesPedido.java`
- Record imutável para transferência de dados
- Encapsula informações completas do pedido
- Usado nas respostas das APIs

### 6. Camada de Persistência (`repositories/`)

#### `PedidoRepository.java`
- Interface JpaRepository para operações CRUD
- Consultas customizadas com @Query
- Implementa validações de segurança nas consultas

### 7. Camada de Serviços (`services/`)

#### `AssistenteSuporteVendas.java`
- Interface do serviço de IA
- Configurado com @SystemMessage para definir personalidade
- Mantém contexto por sessão usando @MemoryId

#### `PedidoService.java`
- Lógica de negócio para operações com pedidos
- Transformação entre entidades e DTOs
- Implementa regras de validação

#### `PedidoTool.java`
- Ferramentas disponíveis para o assistente de IA
- Métodos anotados com @Tool
- Ponte entre IA e serviços de negócio

## Funcionalidades do Assistente de IA

### Capacidades do "Robozinho"

1. **Consulta de Pedidos**
   - Buscar detalhes de pedidos específicos
   - Listar quantidade de pedidos por usuário
   - Verificar status de pedidos

2. **Cancelamento de Pedidos**
   - Cancelar pedidos com validação de identidade
   - Atualizar status no banco de dados
   - Fornecer confirmação da operação

3. **Informações Estatísticas**
   - Quantidade de pedidos por status
   - Valor do pedido mais caro do sistema
   - Análises gerais do sistema

4. **Validação de Segurança**
   - Verificação de identidade antes de mostrar dados
   - Validação de nome e sobrenome do usuário
   - Controle de acesso baseado em propriedade

### Regras de Negócio do Assistente

1. **Identificação Obrigatória**: Antes de acessar detalhes de pedidos, o assistente deve coletar nome, sobrenome e ID do pedido.

2. **Escopo Limitado**: Responde apenas perguntas relacionadas ao sistema de pedidos.

3. **Transparência**: Informa quando não possui determinada informação.

4. **Comportamento**: Amigável, educado, profissional e conciso.

## Configurações da Aplicação

### `application.properties`

```properties
# Servidor
server.port=8080
spring.application.name=suporte-vendas

# LangChain4j - OpenAI
langchain4j.open-ai.chat-model.api-key=demo
langchain4j.open-ai.chat-model.model-name=gpt-4o-mini
langchain4j.open-ai.chat-model.log-requests=true
langchain4j.open-ai.chat-model.log-responses=true

# Banco de Dados PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/suporte-vendas
spring.datasource.username=root
spring.datasource.password=123456
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

# JPA/Hibernate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## Modelo de Dados

### Relacionamentos

```
Usuario (1) ←→ (N) Pedido (N) ←→ (N) Produto
```

### Esquema das Tabelas

#### usuarios
- usuario_id (PK, SERIAL)
- primeiro_nome (VARCHAR)
- ultimo_nome (VARCHAR)
- email (VARCHAR)

#### pedidos
- pedido_id (PK, SERIAL)
- usuario_id (FK)
- criado_em (TIMESTAMP)
- status (VARCHAR)
- valor_total (DECIMAL)

#### produtos
- produto_id (PK, SERIAL)
- nome (VARCHAR)
- descricao (TEXT)
- preco (DECIMAL)

#### pedidos_produtos (Tabela de Junção)
- pedido_id (FK)
- produto_id (FK)

## APIs Disponíveis

### 1. Chat com Assistente
```http
GET /chat?sessionId={id}&message={mensagem}
```

**Exemplo:**
```http
GET /chat?sessionId=user123&message=Qual o status do meu pedido 456?
```

### 2. Quantidade de Pedidos por Usuário
```http
GET /pedidos/?usuarioId={id}
```

### 3. Detalhes de Pedido
```http
GET /pedidos/{pedidoId}?primeiroNome={nome}&ultimoNome={sobrenome}
```

## Padrões e Boas Práticas

### 1. Arquitetura em Camadas
- **Controllers**: Entrada HTTP
- **Services**: Lógica de negócio
- **Repositories**: Acesso a dados
- **Entities**: Modelo de domínio

### 2. Segurança
- Validação de identidade em operações sensíveis
- Controle de acesso baseado em propriedade
- Não exposição de dados de outros usuários

### 3. Separação de Responsabilidades
- Tools como ponte entre IA e serviços
- DTOs para transferência de dados
- Entities apenas para persistência

### 4. Gerenciamento de Estado
- Contexto de conversa por sessão
- Memória limitada por tokens
- Estado da conversa isolado por usuário

## Executando a Aplicação

### Pré-requisitos
1. Java 17+
2. PostgreSQL
3. Maven
4. Chave da API OpenAI (para produção)

### Passos
1. Configurar banco de dados PostgreSQL
2. Ajustar `application.properties`
3. Executar: `mvn spring-boot:run`
4. Acessar: `http://localhost:8080`

### Testes
```bash
mvn test
```

## Considerações de Produção

### 1. Segurança
- Configurar autenticação adequada
- Implementar rate limiting
- Validar entrada do usuário
- Criptografar dados sensíveis

### 2. Performance
- Implementar cache para consultas frequentes
- Otimizar consultas do banco
- Configurar connection pool
- Monitorar uso de tokens da IA

### 3. Monitoramento
- Logs estruturados
- Métricas de performance
- Alertas para falhas
- Monitoramento de custos da IA

### 4. Escalabilidade
- Considerar uso de Redis para sessões
- Implementar load balancing
- Separar leitura e escrita no banco
- Otimizar uso da API de IA

---

**Autor:** Ailton Occhi  
**Versão:** 1.0  
**Data:** 2025