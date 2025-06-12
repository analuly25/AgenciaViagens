USE agencia_viagens;

INSERT INTO Clientes (nome, cpf, passaporte, telefone, email, nacionalidade) VALUES 
('Carla Pereira', '056.659.547.99', NULL,'(11) 98109-5478', 'carla.1234@email.com','NACIONAL'),
('André Ferreira', '651.021.585.17', NULL,'(61) 98433-9968', 'andre7891@gmail.com','NACIONAL'),
('Rafael Zavistoski', NULL, 'US14725833', '(44) 78465-8841', 'rafael.3456@gmail.com','ESTRANGEIRO'),
('Maria Clara Aragão', '057.322.175.09', NULL,'(14) 98541-6341', 'maria.clara45@gmail.com','NACIONAL');

INSERT INTO PacoteViagem (nome, destino, descricao, duracao_dias, preco, tipo) VALUES
('Sol e Mar em Maceió', 'Maceió', 'Pacote com passeios pelas praias e piscinas naturais de Maceió.', 5, 1899.90, 'Nacional'),
('Encantos da Serra Gaúcha', 'Gramado', 'Hospedagem charmosa com passeio de Maria Fumaça e tour de vinícolas.', 4, 2290.00, 'Nacional'),
('Descubra Paris', 'Paris', 'Visita à Torre Eiffel, Museu do Louvre e cruzeiro pelo Rio Sena.', 7, 7999.90, 'Internacional'),
('Aventura Andina', 'Cusco', 'Explore Machu Picchu e a cultura Inca com trilhas e guias locais.', 6, 4890.75, 'Internacional'),
('Férias em Fernando de Noronha', 'Fernando de Noronha', 'Mergulho com tartarugas, praias paradisíacas e trilhas ecológicas.', 6, 5400.00, 'Nacional');

INSERT INTO ServicoAdicional (nome, descricao, preco) VALUES
('Traslado Aeroporto-Hotel', 'Serviço de transporte entre o aeroporto e o hotel.', 180.00),
('Passeio de Barco', 'Passeio turístico de barco pelo destino escolhido.', 250.00),
('Guia Turístico', 'Acompanhamento de guia especializado durante os passeios.', 300.00),
('Seguro Viagem', 'Cobertura para emergências médicas e extravio de bagagem.', 120.00),
('Jantar Especial', 'Jantar em restaurante típico local incluso no pacote.', 200.00);

INSERT INTO Pedido (idClientes, idPacoteViagem, data_viagem, valor_total, status) VALUES
(1, 1, '2025-12-12', 1899.90, 'CONFIRMADO'),
(2, 3, '2026-03-05', 7999.90, 'PENDENTE'),
(3, 5, '2025-12-26', 5400.00, 'CONFIRMADO'),
(4, 4, '2026-01-02', 4890.75, 'CONFIRMADO');


INSERT INTO PedidoServico (Pedido_idPedido, ServicoAdicional_idServicoAdicional, quantidade, preco_unitario) VALUES
(5, 2, 1, 250.00),
(5, 3, 1, 300.00),
(7, 2, 2, 500.00),
(6, 4, 3, 600.00),
(5, 1, 2, 360.00),
(7, 3, 1, 300.00),
(8, 4, 2, 240.00),
(8, 1, 2, 360.00);

