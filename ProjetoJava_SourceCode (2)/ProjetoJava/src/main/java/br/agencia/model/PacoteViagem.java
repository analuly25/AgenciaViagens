package br.agencia.model;

public class PacoteViagem {
    private int idPacoteViagem;
    private String nome;
    private String destino;
    private String descricao;
    private int duracaoDias;
    private double preco;
    private String tipo;

    public PacoteViagem() {

    }

    public PacoteViagem(String nome, String destino, String descricao, int duracaoDias, double preco, String tipo) {
        this.nome = nome;
        this.destino = destino;
        this.descricao = descricao;
        this.duracaoDias = duracaoDias;
        this.preco = preco;
        this.tipo = tipo;
    }
    public void setIdPacoteViagem(int idPacoteViagem) {
        this.idPacoteViagem = idPacoteViagem;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDuracaoDias(int duracaoDias) {
        this.duracaoDias = duracaoDias;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdPacoteViagem() {
        return idPacoteViagem;
    }
    public String getNome() {
        return nome;
    }
    public String getDestino() {
        return destino;
    }
    public String getDescricao() {
        return descricao;
    }
    public int getDuracaoDias() {
        return duracaoDias;
    }
    public double getPreco() {
        return preco;
    }
    public String getTipo() {
        return tipo;
    }
}


