package br.agencia.dao;

import br.agencia.model.PacoteViagem;
import br.agencia.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PacoteViagemDao {

    public void inserir(PacoteViagem pacote) {
        String sql = "INSERT INTO PacoteViagem (nome, destino, descricao, duracao_dias, preco, tipo) VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pacote.getNome());
            stmt.setString(2, pacote.getDestino());
            stmt.setString(3, pacote.getDescricao());
            stmt.setInt(4, pacote.getDuracaoDias());
            stmt.setDouble(5, pacote.getPreco());
            stmt.setString(6, pacote.getTipo());

            stmt.executeUpdate();
            System.out.println("Pacote cadastrado com sucesso!");
        }
            catch (SQLException e) {
            System.out.println("Erro ao inserir pacote: " + e.getMessage());
        }
    }

    public PacoteViagem buscarPorId(int id) {
        PacoteViagem pacote = null;
        String sql = "SELECT * FROM PacoteViagem WHERE idPacoteViagem = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pacote = new PacoteViagem();
                pacote.setIdPacoteViagem(rs.getInt("idPacoteViagem"));
                pacote.setNome(rs.getString("nome"));
                pacote.setDestino(rs.getString("destino"));
                pacote.setDescricao(rs.getString("descricao"));
                pacote.setDuracaoDias(rs.getInt("duracao_dias"));
                pacote.setPreco(rs.getDouble("preco"));
                pacote.setTipo(rs.getString("tipo"));
            }
        }
        catch (Exception e) {
            System.out.println("Erro ao buscar pacote: " + e.getMessage());
        }
        return pacote;
    }

    public List<PacoteViagem> listar() {
        List<PacoteViagem> pacotes = new ArrayList<>();
        String sql = "SELECT * FROM PacoteViagem";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

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
        }
        catch (SQLException e) {
            System.out.println("Erro ao listar pacotes: " + e.getMessage());
        }
        return pacotes;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM PacoteViagem WHERE idPacoteViagem = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Pacote excluído com sucesso!");
        }
        catch (SQLException e) {
            System.out.println("Erro ao excluir pacote: " + e.getMessage());
        }
    }

    public boolean temClientesAssociados(int idPacote) {
        String sql = "SELECT COUNT(*) FROM Pedido WHERE idPacote = ?";
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPacote);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar clientes associados: " + e.getMessage());
        }
        return false;
    }
}


