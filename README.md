# Trabalho - Linguagem e Técnicas de Programação II

Ana Luiza Gomes Santana - 22409471

Bárbara Parente de Carvalho Soares - 22402134


-----------------------------------------------------------------------------------------------------------------------------------

# Sistema de Agência de Viagens 

## 📄 Descrição do Sistema

Este sistema foi desenvolvido com o objetivo de gerenciar o cadastro de **clientes**, **pacotes de viagens** e **serviços adicionais** para uma agência de turismo.  

A aplicação permite:
- O cadastro de **clientes nacionais** (com CPF) e **clientes estrangeiros** (com passaporte), garantindo a validação dos documentos conforme a nacionalidade.
- O registro de **pacotes de viagem** com destino, descrição, duração, preço e tipo (ex: nacional, internacional).
- A associação entre clientes e pacotes, permitindo que um **cliente contrate mais de um pacote** de viagem, por meio de **pedidos** registrados no sistema.
- A inclusão de **serviços adicionais** a cada pedido, como translado, passeios turísticos, aluguel de veículos, entre outros, com controle individual de valor e descrição.

### 🔍 Funcionalidades implementadas:
- Cadastro de clientes (nacionais e estrangeiros)
- Cadastro de pacotes de viagem
- Registro de pedidos (cliente + pacote)
- Inclusão de serviços adicionais em pedidos
- Relatórios:
  - Pacotes contratados por cliente
  - Clientes que contrataram determinado pacote

### 🛠️ Tecnologias utilizadas:
- Linguagem: Java
- Banco de Dados: MySQL
- Conexão: JDBC
- Organização do código: DAO, Model, View (menus via terminal)

## 🖥 Pré‑requisitos

- JDK: 11 ou superior, testado com OpenJDK 17

- Maven: 3.8 ou superior, para compilar o projeto

- MySQL: 8.0 ou superior, porta padrão 3306

Dica: use o MySQL Workbench ou DBeaver para executar os scripts com apenas dois cliques.

### O CÓDIGO PARA RODAR COM A INTERFACE GRÁFICA É O MainGUI
### QUANDO O SISTEMA ABRIR A INTERFACE GRÁFICA, ESPANDE A TELA

## 🛠️ 1. Configurando o banco de dados

1. Crie o schema:

 -- no Workbench (ou terminal)

> SOURCE "Banco de dados/CreateTable.sql";

2. Insira dados de exemplo (opcional):
   
> SOURCE "Banco de dados/INSERT.sql";

3. Verifique credenciais:

O arquivo _src/main/java/br/agencia/util/ConnectionFactory.java_ está configurado assim:

> private static final String URL  = "jdbc:mysql://localhost:3306/agencia_viagens";

> private static final String USER = "root";

> private static final String PASS = "ceub123456"; 

- Ajuste USER, PASS e, se necessário, URL para refletir seu ambiente.

 ## 🚀 2. Compilando o projeto

Abra um terminal na pasta _ProjetoJava_SourceCode(2)/ProjetoJava_ e execute:

> mvn clean package -DskipTests

Isso criará o arquivo:

 > target/AgenciaViagens-1.0-SNAPSHOT-jar-with-dependencies.jar

-- Se estiver usando IntelliJ IDEA ou Eclipse, basta Import → Maven Project, aguardar o download das dependências e executar a classe MainGUI.

## 🧐 4. Resolução de problemas comuns

| Sintoma | Causa Provável | Solução |
| ------ | ------ | ------|
| `java.sql.SQLException: Access denied` | Usuário/senha MySQL incorretos | Ajustar `USER` e `PASS` em `ConnectionFactory.java`|
| `Unknown database 'agencia_viagens'` | Schema não criado | Executar `CreateTable.sql` |
| `No suitable driver` | Dependência MySQL faltando | Verificar se Maven baixou `mysql-connector-j` |
| GUI não abre | JDK abaixo da versão 11 | Atualizar JDK |

## Uso da Interface Gráfica

Após iniciar a aplicação, uma janela com abas será exibida. Cada aba corresponde a uma funcionalidade principal do sistema:

*   **Cliente:** Cadastro, listagem, busca e exclusão de clientes.
*   **Pacote:** Cadastro, listagem, busca e exclusão de pacotes de viagem.
*   **Serviço Adicional:** Cadastro, listagem, busca e exclusão de serviços adicionais.
*   **Pedido:** Cadastro, listagem, busca e exclusão de pedidos (contratação de pacotes).
*   **Relacionamentos:** Telas para contratar pacotes, adicionar serviços a pedidos, consultar pacotes por cliente e clientes por pacote.

Explore as abas para utilizar as funcionalidades do sistema. As validações de entrada de dados serão exibidas através de mensagens de erro, quando aplicável.
