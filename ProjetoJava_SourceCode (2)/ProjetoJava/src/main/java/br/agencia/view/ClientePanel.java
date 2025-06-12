package br.agencia.view;

import br.agencia.dao.ClienteDao;
import br.agencia.model.Cliente;
import br.agencia.model.ClienteEstrangeiro;
import br.agencia.model.ClienteNacional;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClientePanel extends JPanel {
    private JTextField nomeField;
    private JTextField telefoneField;
    private JTextField emailField;
    private JComboBox<String> tipoCombo;
    private JTextField docField;
    private JButton salvarBtn;
    private JTable tabelaClientes;
    private DefaultTableModel tableModel;
    private JTextField buscaIdField;
    private JButton buscarBtn;
    private JButton excluirBtn;

    private ClienteDao clienteDao;

    public ClientePanel() {
        clienteDao = new ClienteDao();
        setLayout(new BorderLayout());
        initComponents();
        carregarClientes();
    }

    private void initComponents() {
        // Painel do formulário
        JPanel form = new JPanel(new GridLayout(5, 2));
        nomeField = new JTextField();
        telefoneField = new JTextField();
        emailField = new JTextField();
        tipoCombo = new JComboBox<>(new String[]{"NACIONAL", "ESTRANGEIRO"});
        docField = new JTextField();

        form.add(new JLabel("Nome:")); form.add(nomeField);
        form.add(new JLabel("Telefone:")); form.add(telefoneField);
        form.add(new JLabel("Email:")); form.add(emailField);
        form.add(new JLabel("Tipo:")); form.add(tipoCombo);
        form.add(new JLabel("CPF/Passaporte:")); form.add(docField);

        // Botões de ação (Salvar, Buscar, Excluir)
        salvarBtn = new JButton("Salvar Cliente");
        salvarBtn.addActionListener(e -> salvarCliente());

        buscaIdField = new JTextField();
        buscarBtn = new JButton("Buscar por ID");
        buscarBtn.addActionListener(e -> buscarCliente());

        excluirBtn = new JButton("Excluir por ID");
        excluirBtn.addActionListener(e -> excluirCliente());

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

        // Tabela de clientes
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Tipo", "Documento", "Email", "Telefone"}, 0);
        tabelaClientes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);

        add(mainNorthPanel, BorderLayout.NORTH); // form + botões
        add(scrollPane, BorderLayout.CENTER); // tabela ocupa o centro
    }

    private void salvarCliente() {
        String nome = nomeField.getText();
        String telefone = telefoneField.getText();
        String email = emailField.getText();
        String tipo = (String) tipoCombo.getSelectedItem();
        String doc = docField.getText();

        // Validações
        if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty() || doc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Formato de e-mail inválido!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ("NACIONAL".equals(tipo) && !isValidCPF(doc)) {
            JOptionPane.showMessageDialog(this, "CPF inválido!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        } else if ("ESTRANGEIRO".equals(tipo) && doc.length() < 5) { // Exemplo de validação simples para passaporte
            JOptionPane.showMessageDialog(this, "Passaporte inválido!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente;
        if ("NACIONAL".equals(tipo)) {
            cliente = new ClienteNacional(nome, telefone, email, doc);
        } else {
            cliente = new ClienteEstrangeiro(nome, telefone, email, doc);
        }

        clienteDao.inserir(cliente);
        JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!");
        carregarClientes();
        limparCampos();
    }

    private void buscarCliente() {
        try {
            int id = Integer.parseInt(buscaIdField.getText());
            Cliente cliente = clienteDao.buscarPorId(id);
            if (cliente != null) {
                tableModel.setRowCount(0);
                tableModel.addRow(new Object[]{cliente.getIdCliente(), cliente.getNome(), cliente.getNacionalidade(), cliente.getDocumento(), cliente.getEmail(), cliente.getTelefone()});
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado!", "Busca", JOptionPane.INFORMATION_MESSAGE);
                carregarClientes(); // Recarrega todos os clientes se não encontrar
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido! Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirCliente() {
        try {
            int id = Integer.parseInt(buscaIdField.getText());
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o cliente com ID " + id + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                clienteDao.excluir(id);
                JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
                carregarClientes();
                limparCampos();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido! Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarClientes() {
        tableModel.setRowCount(0);
        List<Cliente> clientes = clienteDao.listar();
        for (Cliente cliente : clientes) {
            tableModel.addRow(new Object[]{cliente.getIdCliente(), cliente.getNome(), cliente.getNacionalidade(), cliente.getDocumento(), cliente.getEmail(), cliente.getTelefone()});
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        telefoneField.setText("");
        emailField.setText("");
        docField.setText("");
        tipoCombo.setSelectedIndex(0);
        buscaIdField.setText("");
    }

    // Validação de e-mail (simplificada)
    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    // Validação de CPF (simplificada - apenas formato, não dígito verificador)
    private boolean isValidCPF(String cpf) {
        return cpf.matches("\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}");
    }
}


