package br.agencia.dao;

import br.agencia.model.Cliente;
import br.agencia.model.ClienteEstrangeiro;
import br.agencia.model.ClienteNacional;
import br.agencia.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    public void inserir(Cliente cliente) {
        String sql = "INSERT INTO Clientes (nome, cpf, passaporte, telefone, email, nacionalidade) VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, cliente.getNome());

            if (cliente instanceof ClienteNacional) {
                stmt.setString(2, cliente.getDocumento());
                stmt.setString(3, null);
            } else {
                stmt.setString(2, null);
                stmt.setString(3, cliente.getDocumento());
            }

            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEmail());
            stmt.setString(6, cliente.getNacionalidade());

            stmt.executeUpdate();
            System.out.println("Cliente cadastrado com sucesso!");

        }
            catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
        }
    }

    public Cliente buscarPorId(int id) {
        Cliente cliente = null;
        String sql = "SELECT * FROM Clientes WHERE idClientes = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nacionalidade = rs.getString("nacionalidade");
                if ("NACIONAL".equals(nacionalidade)) {
                    cliente = new ClienteNacional(rs.getString("nome"), rs.getString("telefone"), rs.getString("email"), rs.getString("cpf"));
                } else {
                    cliente = new ClienteEstrangeiro(rs.getString("nome"), rs.getString("telefone"), rs.getString("email"), rs.getString("passaporte"));
                }
                cliente.setIdCliente(rs.getInt("idClientes"));
            }
        }
        catch (Exception e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
        }
        return cliente;
    }

    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente;
                String nacionalidade = rs.getString("nacionalidade");
                if ("NACIONAL".equals(nacionalidade)) {
                    cliente = new ClienteNacional(rs.getString("nome"), rs.getString("telefone"), rs.getString("email"), rs.getString("cpf"));
                } else {
                    cliente = new ClienteEstrangeiro(rs.getString("nome"), rs.getString("telefone"), rs.getString("email"), rs.getString("passaporte"));
                }
                cliente.setIdCliente(rs.getInt("idClientes"));
                clientes.add(cliente);
            }
        }
        catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM Clientes WHERE idClientes = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Cliente excluído com sucesso!");
        }
        catch (SQLException e) {
            System.out.println("Erro ao excluir cliente: " + e.getMessage());
        }
    }
}


