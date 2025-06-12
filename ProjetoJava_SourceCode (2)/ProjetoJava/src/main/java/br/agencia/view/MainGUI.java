package br.agencia.view;

import br.agencia.dao.*;
import br.agencia.model.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class MainGUI extends JFrame {

    public MainGUI() {
        setTitle("Sistema de Agência de Viagens");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Cliente", new ClientePanel());
        tabbedPane.add("Pacote", new PacotePanel());
        tabbedPane.add("Serviço Adicional", new ServicoAdicionalPanel());
        tabbedPane.add("Pedido", new PedidoPanel());
        tabbedPane.add("Relacionamentos", new RelacionamentoPanel());

        add(tabbedPane);
        setVisible(true);
    }

    // Método main para iniciar a aplicação
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}


