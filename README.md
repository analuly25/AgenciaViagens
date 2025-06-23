# Sistema de Agência de Viagens

Este documento contém as instruções para configurar e executar o sistema de agência de viagens com interface gráfica (GUI) em seu ambiente local.

## Requisitos

*   Java Development Kit (JDK) 11 ou superior
*   MySQL Server
*   Maven (para compilar o projeto, se necessário)

## Configuração do Banco de Dados

1.  **Crie o banco de dados:**
    Abra seu cliente MySQL (por exemplo, MySQL Workbench, linha de comando) e execute o script `CreateTable.sql` para criar a estrutura do banco de dados.

    ```sql
    SOURCE path/to/CreateTable.sql;
    ```

2.  **Popule o banco de dados (opcional):**
    Se desejar, execute o script `INSERT.sql` para popular o banco de dados com dados de exemplo.

    ```sql
    SOURCE path/to/INSERT.sql;
    ```

3.  **Configure a conexão com o banco de dados:**
    O sistema espera que o banco de dados MySQL esteja rodando localmente na porta padrão (3306) e que as credenciais de acesso sejam `root` para o usuário e `babi2006` para a senha. Se suas credenciais forem diferentes, você precisará editar o arquivo `ConnectionFactory.java` localizado em `src/main/java/br/agencia/util/ConnectionFactory.java` e recompilar o projeto.

    ```java
    private static final String URL = "jdbc:mysql://localhost:3306/agencia_viagens";
    private static final String USER = "root";
    private static final String PASS = "babi2006";
    ```

## Executando a Aplicação

### Opção 1: Executar o JAR (Recomendado)

1.  **Localize o arquivo JAR:**
    O arquivo executável da aplicação está localizado em `target/AgenciaViagens-1.0-SNAPSHOT-jar-with-dependencies.jar`.

2.  **Execute a aplicação:**
    Abra um terminal ou prompt de comando, navegue até o diretório raiz do projeto (`ProjetoJava`) e execute o seguinte comando:

    ```bash
    java -jar target/AgenciaViagens-1.0-SNAPSHOT-jar-with-dependencies.jar
    ```

### Opção 2: Compilar e Executar com Maven

Se você fez alterações no código ou as credenciais do banco de dados, precisará recompilar o projeto.

1.  **Navegue até o diretório do projeto:**
    Abra um terminal ou prompt de comando e navegue até o diretório raiz do projeto (`ProjetoJava`).

2.  **Compile o projeto:**

    ```bash
    mvn clean install
    ```

3.  **Execute a aplicação:**

    ```bash
    mvn exec:java
    ```

## Uso da Interface Gráfica

Após iniciar a aplicação, uma janela com abas será exibida. Cada aba corresponde a uma funcionalidade principal do sistema:

*   **Cliente:** Cadastro, listagem, busca e exclusão de clientes.
*   **Pacote:** Cadastro, listagem, busca e exclusão de pacotes de viagem.
*   **Serviço Adicional:** Cadastro, listagem, busca e exclusão de serviços adicionais.
*   **Pedido:** Cadastro, listagem, busca e exclusão de pedidos (contratação de pacotes).
*   **Relacionamentos:** Telas para contratar pacotes, adicionar serviços a pedidos, consultar pacotes por cliente e clientes por pacote.

Explore as abas para utilizar as funcionalidades do sistema. As validações de entrada de dados serão exibidas através de mensagens de erro, quando aplicável.

