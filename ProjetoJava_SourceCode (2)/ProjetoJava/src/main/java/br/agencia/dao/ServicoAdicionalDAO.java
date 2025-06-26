package br.agencia.dao;

import br.agencia.model.ServicoAdicional;
import br.agencia.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicoAdicionalDAO {

    public void inserir(ServicoAdicional servico) {
        String sql = "INSERT INTO ServicoAdicional (nome, descricao, preco) VALUES (?, ?, ?)";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, servico.getNome());
            stmt.setString(2, servico.getDescricao());
            stmt.setDouble(3, servico.getPreco());

            stmt.executeUpdate();
            System.out.println("Serviço adicional inserido com sucesso!");
        }
            catch (SQLException e) {
            System.out.println("Erro ao inserir serviço adicional: " + e.getMessage());
        }
    }

    public ServicoAdicional buscarPorId(int id) {
        ServicoAdicional servico = null;
        String sql = "SELECT * FROM ServicoAdicional WHERE idServicoAdicional = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                servico = new ServicoAdicional();
                servico.setIdServicoAdicional(rs.getInt("idServicoAdicional"));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setPreco(rs.getDouble("preco"));
            }
        }
        catch (Exception e) {
            System.out.println("Erro ao buscar serviço adicional: " + e.getMessage());
        }
        return servico;
    }

    public List<ServicoAdicional> listar() {
        List<ServicoAdicional> servicos = new ArrayList<>();
        String sql = "SELECT * FROM ServicoAdicional";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ServicoAdicional servico = new ServicoAdicional();
                servico.setIdServicoAdicional(rs.getInt("idServicoAdicional"));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setPreco(rs.getDouble("preco"));
                servicos.add(servico);
            }
        }
        catch (SQLException e) {
            System.out.println("Erro ao listar serviços adicionais: " + e.getMessage());
        }
        return servicos;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM ServicoAdicional WHERE idServicoAdicional = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Serviço adicional excluído com sucesso!");
        }
        catch (SQLException e) {
            System.out.println("Erro ao excluir serviço adicional: " + e.getMessage());
        }
    }
}



