package br.agencia.util;

//Importações para manipular o banco de dados
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Classe responsável por criar conexões com o banco de dados
public class ConnectionFactory {

    // Constantes com os dados necessários para a conexão com o banco
    private static final String URL = "jdbc:mysql://localhost:3306/agencia_viagens";
    private static final String USER = "root";
    private static final String PASS = "ceub123456";

    // Método que retorna uma conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        // Usa o DriverManager para criar e retornar uma conexão com o banco, usando os dados fornecidos
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
