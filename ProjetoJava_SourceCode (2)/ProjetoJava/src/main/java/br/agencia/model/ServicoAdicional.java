package br.agencia.model;

public class ServicoAdicional {
    private int idServicoAdicional;
    private String nome;
    private String descricao;
    private double preco;

    public ServicoAdicional() {

    }

    public ServicoAdicional(String nome, String descricao, double preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    // Getters e Setters
    public int getIdServicoAdicional() { return idServicoAdicional; }
    public void setIdServicoAdicional(int idServicoAdicional) { this.idServicoAdicional = idServicoAdicional; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
}




