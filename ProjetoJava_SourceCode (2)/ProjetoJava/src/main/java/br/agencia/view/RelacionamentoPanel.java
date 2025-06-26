package br.agencia.view;

import br.agencia.dao.ClienteDao;
import br.agencia.dao.PacoteViagemDao;
import br.agencia.dao.PedidoDao;
import br.agencia.dao.ServicoAdicionalDAO;
import br.agencia.model.Cliente;
import br.agencia.model.PacoteViagem;
import br.agencia.model.Pedido;
import br.agencia.model.ServicoAdicional;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class RelacionamentoPanel extends JPanel {
    private JTabbedPane subTabbedPane;

    private ClienteDao clienteDao;
    private PacoteViagemDao pacoteViagemDao;
    private PedidoDao pedidoDao;
    private ServicoAdicionalDAO servicoAdicionalDAO;

    public RelacionamentoPanel() {
        clienteDao = new ClienteDao();
        pacoteViagemDao = new PacoteViagemDao();
        pedidoDao = new PedidoDao();
        servicoAdicionalDAO = new ServicoAdicionalDAO();

        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        subTabbedPane = new JTabbedPane();
        subTabbedPane.addTab("Contratar Pacote", criarPainelContratarPacote());
        subTabbedPane.addTab("Adicionar Serviço", criarPainelAdicionarServico());
        subTabbedPane.addTab("Pacotes por Cliente", criarPainelPacotesPorCliente());
        subTabbedPane.addTab("Clientes por Pacote", criarPainelClientesPorPacote());
        subTabbedPane.addTab("Serviços por Pedido", criarPainelServicosPorPedido());


        add(subTabbedPane, BorderLayout.CENTER);
    }

    private JPanel criarPainelContratarPacote() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JTextField clienteIdField = new JTextField();
        JTextField pacoteIdField = new JTextField();

        JButton contratarBtn = new JButton("Contratar Pacote");
        contratarBtn.addActionListener(e -> {
            try {
                int idCliente = Integer.parseInt(clienteIdField.getText());
                int idPacote = Integer.parseInt(pacoteIdField.getText());

                Cliente cliente = clienteDao.buscarPorId(idCliente);
                PacoteViagem pacote = pacoteViagemDao.buscarPorId(idPacote);

                if (cliente == null) {
                    JOptionPane.showMessageDialog(this, "Cliente não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (pacote == null) {
                    JOptionPane.showMessageDialog(this, "Pacote não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Pedido pedido = new Pedido(idCliente, idPacote, LocalDate.now(), pacote.getPreco());
                pedidoDao.cadastrar(pedido);
                JOptionPane.showMessageDialog(this, "Pacote contratado com sucesso!");
                clienteIdField.setText("");
                pacoteIdField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "IDs devem ser números válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel("ID Cliente:")); panel.add(clienteIdField);
        panel.add(new JLabel("ID Pacote:")); panel.add(pacoteIdField);
        panel.add(new JLabel()); panel.add(contratarBtn);

        return panel;
    }

    private JPanel criarPainelAdicionarServico() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JTextField pedidoIdField = new JTextField();
        JTextField servicoIdField = new JTextField();

        JButton adicionarBtn = new JButton("Adicionar Serviço");
        adicionarBtn.addActionListener(e -> {
            try {
                int idPedido = Integer.parseInt(pedidoIdField.getText());
                int idServico = Integer.parseInt(servicoIdField.getText());

                Pedido pedido = pedidoDao.buscarPorId(idPedido);
                ServicoAdicional servico = servicoAdicionalDAO.buscarPorId(idServico);

                if (pedido == null) {
                    JOptionPane.showMessageDialog(this, "Pedido não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (servico == null) {
                    JOptionPane.showMessageDialog(this, "Serviço não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                pedidoDao.adicionarServico(idPedido, idServico);
                JOptionPane.showMessageDialog(this, "Serviço adicionado ao pedido com sucesso!");
                pedidoIdField.setText("");
                servicoIdField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "IDs devem ser números válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel("ID Pedido:")); panel.add(pedidoIdField);
        panel.add(new JLabel("ID Serviço:")); panel.add(servicoIdField);
        panel.add(new JLabel()); panel.add(adicionarBtn);

        return panel;
    }

    private JPanel criarPainelServicosPorPedido() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new FlowLayout());
        JTextField pedidoIdField = new JTextField(10);
        JButton buscarBtn = new JButton("Buscar");
        form.add(new JLabel("ID Pedido:"));
        form.add(pedidoIdField);
        form.add(buscarBtn);

        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"ID Serviço", "Nome", "Descrição", "Preço"}, 0);
        JTable tabela = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabela);

        buscarBtn.addActionListener(e -> {
            try {
                int idPedido = Integer.parseInt(pedidoIdField.getText());
                List<ServicoAdicional> servicos = pedidoDao.buscarServicosPorPedido(idPedido);
                tableModel.setRowCount(0);
                if (servicos.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhum serviço encontrado para este pedido.", "Busca", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (ServicoAdicional servico : servicos) {
                        tableModel.addRow(new Object[]{
                                servico.getIdServicoAdicional(),
                                servico.getNome(),
                                servico.getDescricao(),
                                servico.getPreco()
                        });
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido! Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(form, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }


    private JPanel criarPainelPacotesPorCliente() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new FlowLayout());
        JTextField clienteIdField = new JTextField(10);
        JButton buscarBtn = new JButton("Buscar");
        form.add(new JLabel("ID Cliente:"));
        form.add(clienteIdField);
        form.add(buscarBtn);

        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"ID Pacote", "Nome Pacote", "Destino", "Preço"}, 0);
        JTable tabela = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabela);

        buscarBtn.addActionListener(e -> {
            try {
                int idCliente = Integer.parseInt(clienteIdField.getText());
                List<PacoteViagem> pacotes = pedidoDao.buscarPacotesPorCliente(idCliente);
                tableModel.setRowCount(0);
                if (pacotes.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhum pacote encontrado para este cliente.", "Busca", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (PacoteViagem pacote : pacotes) {
                        tableModel.addRow(new Object[]{pacote.getIdPacoteViagem(), pacote.getNome(), pacote.getDestino(), pacote.getPreco()});
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID Cliente inválido! Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(form, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel criarPainelClientesPorPacote() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new FlowLayout());
        JTextField pacoteIdField = new JTextField(10);
        JButton buscarBtn = new JButton("Buscar");
        form.add(new JLabel("ID Pacote:"));
        form.add(pacoteIdField);
        form.add(buscarBtn);

        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"ID Cliente", "Nome Cliente", "Documento"}, 0);
        JTable tabela = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabela);

        buscarBtn.addActionListener(e -> {
            try {
                int idPacote = Integer.parseInt(pacoteIdField.getText());
                List<Cliente> clientes = pedidoDao.buscarClientesPorPacote(idPacote);
                tableModel.setRowCount(0);
                if (clientes.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhum cliente encontrado para este pacote.", "Busca", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (Cliente cliente : clientes) {
                        tableModel.addRow(new Object[]{cliente.getIdCliente(), cliente.getNome(), cliente.getDocumento()});
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID Pacote inválido! Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(form, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
}


