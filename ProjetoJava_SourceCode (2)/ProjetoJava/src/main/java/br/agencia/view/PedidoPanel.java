package br.agencia.view;

import br.agencia.dao.PedidoDao;
import br.agencia.dao.PacoteViagemDao;
import br.agencia.model.Pedido;
import br.agencia.model.PacoteViagem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PedidoPanel extends JPanel {
    private JTextField clienteIdField;
    private JTextField pacoteIdField;
    private JButton salvarBtn;
    private JTable tabelaPedidos;
    private DefaultTableModel tableModel;
    private JTextField buscaIdField;
    private JButton buscarBtn;
    private JButton excluirBtn;

    private PedidoDao pedidoDao;
    private PacoteViagemDao pacoteViagemDao;

    public PedidoPanel() {
        pedidoDao = new PedidoDao();
        pacoteViagemDao = new PacoteViagemDao();
        setLayout(new BorderLayout());
        initComponents();
        carregarPedidos();
    }

    private void initComponents() {
        // Painel do formulário
        JPanel form = new JPanel(new GridLayout(2, 2));
        clienteIdField = new JTextField();
        pacoteIdField = new JTextField();

        form.add(new JLabel("ID Cliente:")); form.add(clienteIdField);
        form.add(new JLabel("ID Pacote:")); form.add(pacoteIdField);

        // Botões de ação (Salvar, Buscar, Excluir)
        salvarBtn = new JButton("Salvar Pedido");
        salvarBtn.addActionListener(e -> salvarPedido());

        buscaIdField = new JTextField();
        buscarBtn = new JButton("Buscar por ID 🔍");
        buscarBtn.addActionListener(e -> buscarPedido());

        excluirBtn = new JButton("Excluir por ID ❌");
        excluirBtn.addActionListener(e -> excluirPedido());

        JPanel botoesFormPanel = new JPanel();
        botoesFormPanel.add(salvarBtn);

        JPanel buscaExclusaoPanel = new JPanel(new GridLayout(1, 4));
        buscaExclusaoPanel.add(new JLabel("ID:"));
        buscaExclusaoPanel.add(buscaIdField);
        buscaExclusaoPanel.add(buscarBtn);
        buscaExclusaoPanel.add(excluirBtn);

        // Painel que agrupa formulário + botões
        JPanel topoPanel = new JPanel(new BorderLayout());
        topoPanel.add(form, BorderLayout.CENTER);
        topoPanel.add(botoesFormPanel, BorderLayout.SOUTH);

        JPanel mainNorthPanel = new JPanel(new BorderLayout());
        mainNorthPanel.add(topoPanel, BorderLayout.NORTH);
        mainNorthPanel.add(buscaExclusaoPanel, BorderLayout.SOUTH);

        // Tabela de pedidos
        tableModel = new DefaultTableModel(new Object[]{"ID Pedido", "ID Cliente", "ID Pacote", "Data", "Preço"}, 0);
        tabelaPedidos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaPedidos);

        add(mainNorthPanel, BorderLayout.NORTH); // form + botões
        add(scrollPane, BorderLayout.CENTER); // tabela ocupa o centro
    }

    private void salvarPedido() {
        // Validações
        if (clienteIdField.getText().isEmpty() || pacoteIdField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "IDs de Cliente e Pacote são obrigatórios!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int idCliente = Integer.parseInt(clienteIdField.getText());
            int idPacote = Integer.parseInt(pacoteIdField.getText());

            PacoteViagem pacote = pacoteViagemDao.buscarPorId(idPacote);
            if (pacote == null) {
                JOptionPane.showMessageDialog(this, "Pacote com ID " + idPacote + " não encontrado!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double preco = pacote.getPreco();

            Pedido pedido = new Pedido(idCliente, idPacote, LocalDate.now(), preco);
            pedidoDao.cadastrar(pedido);
            JOptionPane.showMessageDialog(this, "Pedido salvo com sucesso!");
            carregarPedidos();
            limparCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "IDs devem ser números válidos!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarPedido() {
        try {
            int id = Integer.parseInt(buscaIdField.getText());
            Pedido pedido = pedidoDao.buscarPorId(id);
            if (pedido != null) {
                tableModel.setRowCount(0);
                tableModel.addRow(new Object[]{pedido.getIdPedido(), pedido.getIdCliente(), pedido.getIdPacote(), pedido.getDataPedido(), pedido.getPrecoTotal()});
            } else {
                JOptionPane.showMessageDialog(this, "Pedido não encontrado!", "Busca", JOptionPane.INFORMATION_MESSAGE);
                carregarPedidos();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido! Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirPedido() {
        try {
            int id = Integer.parseInt(buscaIdField.getText());
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o pedido com ID " + id + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                pedidoDao.excluir(id);
                JOptionPane.showMessageDialog(this, "Pedido excluído com sucesso!");
                carregarPedidos();
                limparCampos();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido! Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarPedidos() {
        tableModel.setRowCount(0);
        List<Pedido> pedidos = pedidoDao.listar();
        for (Pedido pedido : pedidos) {
            tableModel.addRow(new Object[]{pedido.getIdPedido(), pedido.getIdCliente(), pedido.getIdPacote(), pedido.getDataPedido(), pedido.getPrecoTotal()});
        }
    }

    private void limparCampos() {
        clienteIdField.setText("");
        pacoteIdField.setText("");
        buscaIdField.setText("");
    }
}


