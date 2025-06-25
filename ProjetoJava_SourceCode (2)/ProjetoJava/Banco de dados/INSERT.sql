USE agencia_viagens;

INSERT INTO Clientes (nome, cpf, passaporte, telefone, email, nacionalidade) VALUES 
('Carla Pereira', '056.659.547.99', NULL,'(11) 98109-5478', 'carla.pereira34@email.com','NACIONAL'),
('André Ferreira', '651.021.585.17', NULL,'(61) 98433-9968', 'ferreiraandre@gmail.com','NACIONAL'),
('Rafael Zavistoski', NULL, 'US14725833', '(44) 78465-8841', 'rafael.zavitoski@gmail.com','ESTRANGEIRO'),
('Maria Clara Aragão', '057.322.175.09', NULL,'(14) 98541-6341', 'maria.clara45@gmail.com','NACIONAL'),
('Ana Maria Souza', NULL, 'US34629828', '(44) 37382-5282','sariasouza.ana@gmail.com', 'ESTRANGEIRO'),
('Carlos André Gomes', '234.987.068.00', NULL, '(21) 98876-0910', 'andregomes.carlos@gmail.com', 'NACIONAL'),
('Bruno Andrade', '098.876.750.76', NULL, '(82) 90919-7382', 'andradebruno@gmail.com', 'NACIONAL'),
('Laura Carvalho', '609.765.504.32', NULL, '(61) 90818-7388', 'laura.carvalho@gmail.com', 'NACIONAL'),
('Izabel França', NULL, 'US07271595', '(44) 72548-7388', 'izabelfranca90@gmail.com', 'ESTRANGEIRO'),
('João Pedro dos Santos', '091.728.382.78', '(21) 96721-2346', 'jpedrosantos@gmail.com', 'NACIONAL'),
('Júlia Aragão', '826.409.637.12', '(15) 98788-0919', 'aragaojulia@gmail.com', 'NACIONAL')
('Bruna Smith', NULL, 'US9102739', '(44) 72819-0183', 'smithbru@gmail.com', 'ESTRANGEIRO')
('Felipe Queiroz', '444.098.267.12', '(61) 90876-0919', 'fequeiroz@gmail.com', 'NACIONAL');

INSERT INTO PacoteViagem (nome, destino, descricao, duracao_dias, preco, tipo) VALUES
('Sol e Mar em Maceió', 'Maceió', 'Pacote com passeios pelas praias e piscinas naturais de Maceió.', 5, 1899.90, 'Nacional'),
('Encantos da Serra Gaúcha', 'Gramado', 'Hospedagem charmosa com passeio de Maria Fumaça e tour de vinícolas.', 4, 2290.00, 'Nacional'),
('Descubra Paris', 'Paris', 'Visita à Torre Eiffel, Museu do Louvre e cruzeiro pelo Rio Sena.', 7, 7999.90, 'Internacional'),
('Aventura Andina', 'Cusco', 'Explore Machu Picchu e a cultura Inca com trilhas e guias locais.', 6, 4890.75, 'Internacional'),
('Férias em Fernando de Noronha', 'Fernando de Noronha', 'Mergulho com tartarugas, praias paradisíacas e trilhas ecológicas.', 6, 5400.00, 'Nacional'),
('São Paulo Cultural','São Paulo','Visita ao MASP, Jardim Botânicon Sampa Sky e Mercado Municipal.', 5, 3500.00, 'Nacional'),
('Caribe dos Sonhos', 'Punta Cana', 'Resorte all-inclusive com traslado e passeio de catamarâ ', 6, 9000.00, 'Internacional'),
('Chapada dos Veadeiros', 'Chapada dos Veadeiros', 'Pacote com trilha guiada, cachoeiras e transporte.', 5, 1200.00, 'Nacional'),
('Inverno em Bariloche', 'Bariloche' ,'Hospedagem próxima ao centro de esqui e passeio ao Cerro Catedral.', 6, 6500.00, 'Internacional'),
('Maragogi Azul-Turquesa', 'Maragogi', 'Passeio às piscinas naturais, traslado e hospedagem.', 4, 2900.00, 'Nacional'),
('Pantanal Selvagem', 'Barão do Melgaço', 'Lodge ecológico, safáris fotográficos, alimentação completa.', 4, 2000.00, 'Nacional'),
('Rio 360°', 'Rio de Janeiro', 'Visita ao Cristo Redentor, Pão de Açúcar e Copacabana Palace'. 5, 4000.00, 'Nacional'),
('Orlando Diversão Total', 'Orlando', 'Hospedagem para 4 parques da Disney e hospedagem próxima.', 6, 8000.00, 'Internacional'),
('Encantos de Marrakech', 'Marrocos', 'City tour guiado, passeio ao deserto e jantar típico.', 7, 5000.00, 'Internacional')
  ;

INSERT INTO ServicoAdicional (nome, descricao, preco) VALUES
('Traslado Aeroporto-Hotel', 'Serviço de transporte entre o aeroporto e o hotel.', 180.00),
('Passeio de Barco', 'Passeio turístico de barco pelo destino escolhido.', 250.00),
('Guia Turístico', 'Acompanhamento de guia especializado durante os passeios.', 300.00),
('City Tour', 'Passeio pela cidade com guia.', 200.00),
('Seguro Viagem', 'Cobertura para emergências médicas e extravio de bagagem.', 120.00),
('Jantar Especial', 'Jantar em restaurante típico local incluso no pacote.', 200.00),
('Café da Manhã no Quarto', 'Café da manhã será levado no quarto do hóspede.', 100.00),
('Aluguel de Veículo', 'Aluguel de veículo para maior liberdade de locomoção.', 1000,00),
('Kit Viagem', 'Kit para maior conforto na viagem com máscara de dormir, manta e fone de ouvido.', 80.00),
('Reserva de Spa', 'Horário marcado em Spa do hotel.', 150.00),
('Chip Internacional', 'Emissão de chip internacional para telefone com banda larga.', 100.00),
('Consultoria', 'Consultoria para emissão de passaporte e visto.', 250.00);

INSERT INTO Pedido (idClientes, idPacoteViagem, data_viagem, valor_total, status) VALUES
(1, 1, '2025-12-12', 1899.90, 'CONFIRMADO'),
(2, 3, '2026-03-05', 7999.90, 'PENDENTE'),
(3, 5, '2025-12-26', 5400.00, 'CONFIRMADO'),
(4, 4, '2026-01-02', 4890.75, 'CONFIRMADO');


INSERT INTO PedidoServico (Pedido_idPedido, ServicoAdicional_idServicoAdicional, quantidade, preco_unitario) VALUES
(1, 2, 1, 250.00),
(1, 3, 1, 300.00),
(2, 2, 2, 500.00),
(3, 4, 3, 600.00),
(1, 1, 2, 360.00),
(2, 3, 1, 300.00),
(4, 4, 2, 240.00),
(4, 1, 2, 360.00);

