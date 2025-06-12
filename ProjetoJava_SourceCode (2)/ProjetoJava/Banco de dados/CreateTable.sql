CREATE DATABASE IF NOT EXISTS agencia_viagens;
USE agencia_viagens;

-- Tabela de Clientes
CREATE TABLE IF NOT EXISTS Clientes(
   idClientes INT NOT NULL AUTO_INCREMENT,
   nome VARCHAR(45) NOT NULL,
   cpf  VARCHAR(14) NOT NULL,
   passaporte VARCHAR(45) NOT NULL,
   telefone  VARCHAR(45) NOT NULL,
   email  VARCHAR(45) NOT NULL,
   nacionalidade  ENUM('NACIONAL', 'ESTRANGEIRO') NOT NULL,
   data_cadastro  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT chk_documento CHECK (
        (nacionalidade = 'NACIONAL' AND cpf IS NOT NULL AND passaporte IS NULL) OR
        (nacionalidade = 'ESTRANGEIRO' AND passaporte IS NOT NULL AND cpf IS NULL)
    ),
  PRIMARY KEY (`idClientes`),
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) VISIBLE,
  UNIQUE INDEX `passaporte_UNIQUE` (`passaporte` ASC) VISIBLE);
 
 ALTER TABLE Clientes 
MODIFY cpf VARCHAR(14);

ALTER TABLE Clientes 
MODIFY passaporte VARCHAR(45);

-- Tabela de Pacotes de Viagem
CREATE TABLE IF NOT EXISTS PacoteViagem (
   idPacoteViagem  INT AUTO_INCREMENT PRIMARY KEY,
   nome  VARCHAR(45) NOT NULL,
   destino  VARCHAR(45) NOT NULL,
   descricao  TEXT NOT NULL,
   duracao_dias  INT NOT NULL,
   preco  DECIMAL(10,2) NOT NULL,
   tipo  ENUM('Nacional', 'Internacional') NOT NULL,
   
  CONSTRAINT chk_preco_positivo CHECK (preco > 0));
  
-- Tabela de Pedidos
CREATE TABLE IF NOT EXISTS Pedido(
  idPedido INT NOT NULL AUTO_INCREMENT,
  idClientes INT NOT NULL,
  idPacoteViagem INT NOT NULL,
  data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  data_viagem DATE,
  valor_total DECIMAL(10, 2) NOT NULL,
  status ENUM('PENDENTE', 'CONFIRMADO', 'CANCELADO') DEFAULT 'PENDENTE',
  PRIMARY KEY (`idPedido`),
  FOREIGN KEY (idClientes) REFERENCES Clientes(idClientes) ON DELETE RESTRICT,
  FOREIGN KEY (idPacoteViagem) REFERENCES PacoteViagem(idPacoteViagem) ON DELETE RESTRICT,
    
  CONSTRAINT chk_valor_total_positivo CHECK (valor_total > 0)
);

ALTER TABLE Pedido
MODIFY  valor_total DECIMAL(10, 2);

ALTER TABLE Pedido
MODIFY  valor_total DOUBLE;

-- Tabela de Serviços
CREATE TABLE IF NOT EXISTS ServicoAdicional (
  idServicoAdicional INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  descricao TEXT NOT NULL,
  preco DECIMAL(10,2) NOT NULL,
  CONSTRAINT chk_preco_servico_positivo CHECK (preco > 0),
  PRIMARY KEY (`idServicoAdicional`));

-- Tabela de Pedido Serviço
CREATE TABLE IF NOT EXISTS PedidoServico (
  Pedido_idPedido INT NOT NULL,
  ServicoAdicional_idServicoAdicional INT NOT NULL,
  quantidade INT NOT NULL DEFAULT 1,
  preco_unitario DECIMAL(10,2) NOT NULL,
 
  PRIMARY KEY (`Pedido_idPedido`, `ServicoAdicional_idServicoAdicional`),
  FOREIGN KEY (Pedido_idPedido) REFERENCES Pedido(idPedido) ON DELETE CASCADE,
  FOREIGN KEY (ServicoAdicional_idServicoAdicional) REFERENCES ServicoAdicional(idServicoAdicional) ON DELETE RESTRICT,
  CONSTRAINT chk_quantidade_positiva CHECK (quantidade > 0),
  CONSTRAINT chk_preco_unitario_positivo CHECK (preco_unitario > 0)
);
CREATE INDEX idx_clientes_nome ON Clientes(nome);
CREATE INDEX idx_clientes_nacionalidade ON Clientes(nacionalidade);
CREATE INDEX idx_pacote_destino ON PacoteViagem(destino);
CREATE INDEX idx_pacote_tipo ON PacoteViagem(tipo);
CREATE INDEX idx_pedido_cliente ON Pedido(idClientes);
CREATE INDEX idx_pedido_data ON Pedido(data_viagem);



