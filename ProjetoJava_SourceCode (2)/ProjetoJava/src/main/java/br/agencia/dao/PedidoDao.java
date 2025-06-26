package br.agencia.dao;

import br.agencia.model.Cliente;
import br.agencia.model.ClienteEstrangeiro;
import br.agencia.model.ClienteNacional;
import br.agencia.model.PacoteViagem;
import br.agencia.model.Pedido;
import br.agencia.util.ConnectionFactory;
import br.agencia.model.ServicoAdicional;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDao {

    public void cadastrar(Pedido pedido) {
        String sql = "INSERT INTO pedido (idClientes, idPacoteViagem, data_pedido, valor_total) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.setInt(2, pedido.getIdPacote());
            stmt.setDate(3, java.sql.Date.valueOf(pedido.getDataPedido()));
            stmt.setDouble(4, pedido.getPrecoTotal());

            stmt.executeUpdate();
            System.out.println("Pedido cadastrado com sucesso!");

        }
            catch (SQLException e) {
            System.out.println("Erro ao cadastrar pedido: " + e.getMessage());
        }
    }

    public Pedido buscarPorId(int id) {
        Pedido pedido = null;
        String sql = "SELECT * FROM Pedido WHERE idPedido = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("idPedido"));
                pedido.setIdCliente(rs.getInt("idClientes"));
                pedido.setIdPacote(rs.getInt("idPacoteViagem"));
                pedido.setDataPedido(rs.getDate("data_pedido").toLocalDate());
                pedido.setPrecoTotal(rs.getDouble("valor_total"));
            }
        }
        catch (Exception e) {
            System.out.println("Erro ao buscar pedido: " + e.getMessage());
        }
        return pedido;
    }

    public List<Pedido> listar() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedido";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("idPedido"));
                pedido.setIdCliente(rs.getInt("idClientes"));
                pedido.setIdPacote(rs.getInt("idPacoteViagem"));
                pedido.setDataPedido(rs.getDate("data_pedido").toLocalDate());
                pedido.setPrecoTotal(rs.getDouble("valor_total"));
                pedidos.add(pedido);
            }
        }
        catch (SQLException e) {
            System.out.println("Erro ao listar pedidos: " + e.getMessage());
        }
        return pedidos;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM Pedido WHERE idPedido = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Pedido excluído com sucesso!");
        }
        catch (SQLException e) {
            System.out.println("Erro ao excluir pedido: " + e.getMessage());
        }
    }

    public List<PacoteViagem> buscarPacotesPorCliente(int idCliente) {
        List<PacoteViagem> pacotes = new ArrayList<>();
        String sql = "SELECT pv.* FROM PacoteViagem pv JOIN Pedido p ON pv.idPacoteViagem = p.idPacoteViagem WHERE p.idClientes = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PacoteViagem pacote = new PacoteViagem();
                pacote.setIdPacoteViagem(rs.getInt("idPacoteViagem"));
                pacote.setNome(rs.getString("nome"));
                pacote.setDestino(rs.getString("destino"));
                pacote.setDescricao(rs.getString("descricao"));
                pacote.setDuracaoDias(rs.getInt("duracao_dias"));
                pacote.setPreco(rs.getDouble("preco"));
                pacote.setTipo(rs.getString("tipo"));
                pacotes.add(pacote);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pacotes por cliente: " + e.getMessage());
        }
        return pacotes;
    }

    public List<Cliente> buscarClientesPorPacote(int idPacote) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT c.*, cn.cpf, ce.passaporte FROM Cliente c " +
                     "JOIN Pedido p ON c.idClientes = p.idClientes " +
                     "LEFT JOIN ClienteNacional cn ON c.idClientes = cn.idClientes " +
                     "LEFT JOIN ClienteEstrangeiro ce ON c.idClientes = ce.idClientes " +
                     "WHERE p.idPacoteViagem = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPacote);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente;
                String tipo = rs.getString("nacionalidade"); // Assumindo que você tem uma coluna \'tipo\' na tabela Cliente
                if ("NACIONAL".equals(tipo)) {
                    cliente = new ClienteNacional(rs.getString("nome"), rs.getString("telefone"), rs.getString("email"), rs.getString("cpf"));
                } else {
                    cliente = new ClienteEstrangeiro(rs.getString("nome"), rs.getString("telefone"), rs.getString("email"), rs.getString("passaporte"));
                }
                cliente.setIdCliente(rs.getInt("idClientes"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar clientes por pacote: " + e.getMessage());
        }
        return clientes;
    }

    public void adicionarServico(int idPedido, int idServico) {
        String sql = "INSERT INTO PedidoServico (Pedido_idPedido, ServicoAdicional_idServicoAdicional) VALUES (?, ?)";
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, idPedido);
            stmt.setInt(2, idServico);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar serviço ao pedido: " + e.getMessage());
        }
    }

    public List<ServicoAdicional> buscarServicosPorPedido(int idPedido) {
        List<ServicoAdicional> lista = new ArrayList<>();
        String sql = "SELECT s.idServicoAdicional, s.nome, s.descricao, ps.quantidade, ps.preco_unitario " +
                "FROM PedidoServico ps " +
                "JOIN ServicoAdicional s ON ps.ServicoAdicional_idServicoAdicional = s.idServicoAdicional " +
                "WHERE ps.Pedido_idPedido = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ServicoAdicional servico = new ServicoAdicional(
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDouble("preco_unitario")
                );
                servico.setIdServicoAdicional(rs.getInt("idServicoAdicional"));
                lista.add(servico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}


