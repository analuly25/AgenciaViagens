package br.agencia.view;

import br.agencia.dao.ServicoAdicionalDAO;
import br.agencia.model.ServicoAdicional;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ServicoAdicionalPanel extends JPanel {
    private JTextField nomeField;
    private JTextField descricaoField;
    private JTextField precoField;
    private JButton salvarBtn;
    private JTable tabelaServicos;
    private DefaultTableModel tableModel;

    private ServicoAdicionalDAO servicoAdicionalDAO;

    public ServicoAdicionalPanel() {
        servicoAdicionalDAO = new ServicoAdicionalDAO();
        setLayout(new BorderLayout());
        initComponents();
        carregarServicos();
    }

    private void initComponents() {
        // Painel do formulário
        JPanel form = new JPanel(new GridLayout(3, 2));
        nomeField = new JTextField();
        descricaoField = new JTextField();
        precoField = new JTextField();

        form.add(new JLabel("Nome:")); form.add(nomeField);
        form.add(new JLabel("Descrição:")); form.add(descricaoField);
        form.add(new JLabel("Preço:")); form.add(precoField);

        // Botão de salvar
        salvarBtn = new JButton("Salvar Serviço");
        salvarBtn.addActionListener(e -> salvarServico());

        // Painel que agrupa formulário + botão
        JPanel topoPanel = new JPanel(new BorderLayout());
        topoPanel.add(form, BorderLayout.CENTER);

        JPanel botoesPanel = new JPanel();
        botoesPanel.add(salvarBtn);
        topoPanel.add(botoesPanel, BorderLayout.SOUTH);

        // Tabela de serviços adicionais
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Descrição", "Preço"}, 0);
        tabelaServicos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaServicos);

        add(topoPanel, BorderLayout.NORTH); // form + botão
        add(scrollPane, BorderLayout.CENTER); // tabela ocupa o centro
    }

    private void salvarServico() {
        // Validações
        if (nomeField.getText().isEmpty() || precoField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e Preço são campos obrigatórios!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String nome = nomeField.getText();
            String descricao = descricaoField.getText();
            double preco = Double.parseDouble(precoField.getText());

            ServicoAdicional servico = new ServicoAdicional(nome, descricao, preco);
            servicoAdicionalDAO.inserir(servico);
            JOptionPane.showMessageDialog(this, "Serviço Adicional salvo com sucesso!");
            carregarServicos();
            limparCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço deve ser um número válido!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarServicos() {
        tableModel.setRowCount(0);
        List<ServicoAdicional> servicos = servicoAdicionalDAO.listar();
        for (ServicoAdicional servico : servicos) {
            tableModel.addRow(new Object[]{servico.getIdServico(), servico.getNome(), servico.getDescricao(), servico.getPreco()});
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        descricaoField.setText("");
        precoField.setText("");
    }
}


