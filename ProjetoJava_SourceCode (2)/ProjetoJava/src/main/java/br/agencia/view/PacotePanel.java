package br.agencia.view;

import br.agencia.dao.PacoteViagemDao;
import br.agencia.model.PacoteViagem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PacotePanel extends JPanel {
    private JTextField nomeField;
    private JTextField destinoField;
    private JTextField descField;
    private JTextField duracaoField;
    private JTextField precoField;
    private JComboBox<String> tipoCombo;
    private JButton salvarBtn;
    private JTable tabelaPacotes;
    private DefaultTableModel tableModel;
    private JTextField buscaIdField;
    private JButton buscarBtn;
    private JButton excluirBtn;

    private PacoteViagemDao pacoteDao;

    public PacotePanel() {
        pacoteDao = new PacoteViagemDao();
        setLayout(new BorderLayout());
        initComponents();
        carregarPacotes();
    }

    private void initComponents() {
        // Painel do formulário
        JPanel form = new JPanel(new GridLayout(6, 2));
        nomeField = new JTextField();
        destinoField = new JTextField();
        descField = new JTextField();
        duracaoField = new JTextField();
        precoField = new JTextField();
        tipoCombo = new JComboBox<>(new String[]{"NACIONAL", "INTERNACIONAL"});

        form.add(new JLabel("Nome:")); form.add(nomeField);
        form.add(new JLabel("Destino:")); form.add(destinoField);
        form.add(new JLabel("Descrição:")); form.add(descField);
        form.add(new JLabel("Duração (dias):")); form.add(duracaoField);
        form.add(new JLabel("Preço:")); form.add(precoField);
        form.add(new JLabel("Tipo:")); form.add(tipoCombo);

        // Botões de ação (Salvar, Buscar, Excluir)
        salvarBtn = new JButton("Salvar Pacote");
        salvarBtn.addActionListener(e -> salvarPacote());

        buscaIdField = new JTextField();
        buscarBtn = new JButton("Buscar por ID 🔍");
        buscarBtn.addActionListener(e -> buscarPacote());

        excluirBtn = new JButton("Excluir por ID ❌");
        excluirBtn.addActionListener(e -> excluirPacote());

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

        // Tabela de pacotes
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Destino", "Preço", "Tipo"}, 0);
        tabelaPacotes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaPacotes);

        add(mainNorthPanel, BorderLayout.NORTH); // form + botões
        add(scrollPane, BorderLayout.CENTER); // tabela ocupa o centro
    }

    private void salvarPacote() {
        // Validações
        if (nomeField.getText().isEmpty() || destinoField.getText().isEmpty() || precoField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome, Destino e Preço são campos obrigatórios!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String nome = nomeField.getText();
            String destino = destinoField.getText();
            String desc = descField.getText();
            int duracao = Integer.parseInt(duracaoField.getText());
            double preco = Double.parseDouble(precoField.getText());
            String tipo = (String) tipoCombo.getSelectedItem();

            PacoteViagem pacote = new PacoteViagem(nome, destino, desc, duracao, preco, tipo);
            pacoteDao.inserir(pacote);
            JOptionPane.showMessageDialog(this, "Pacote salvo com sucesso!");
            carregarPacotes();
            limparCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Duração e Preço devem ser números válidos!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarPacote() {
        try {
            int id = Integer.parseInt(buscaIdField.getText());
            PacoteViagem pacote = pacoteDao.buscarPorId(id);
            if (pacote != null) {
                tableModel.setRowCount(0);
                tableModel.addRow(new Object[]{pacote.getIdPacoteViagem(), pacote.getNome(), pacote.getDestino(), pacote.getPreco(), pacote.getTipo()});
            } else {
                JOptionPane.showMessageDialog(this, "Pacote não encontrado!", "Busca", JOptionPane.INFORMATION_MESSAGE);
                carregarPacotes();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido! Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirPacote() {
        try {
            int id = Integer.parseInt(buscaIdField.getText());
            if (pacoteDao.temClientesAssociados(id)) {
                JOptionPane.showMessageDialog(this, "Não é possível excluir o pacote, pois existem clientes associados a ele.", "Erro de Exclusão", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o pacote com ID " + id + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                pacoteDao.excluir(id);
                JOptionPane.showMessageDialog(this, "Pacote excluído com sucesso!");
                carregarPacotes();
                limparCampos();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido! Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarPacotes() {
        tableModel.setRowCount(0);
        List<PacoteViagem> pacotes = pacoteDao.listar();
        for (PacoteViagem pacote : pacotes) {
            tableModel.addRow(new Object[]{pacote.getIdPacoteViagem(), pacote.getNome(), pacote.getDestino(), pacote.getPreco(), pacote.getTipo()});
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        destinoField.setText("");
        descField.setText("");
        duracaoField.setText("");
        precoField.setText("");
        tipoCombo.setSelectedIndex(0);
        buscaIdField.setText("");
    }
}


