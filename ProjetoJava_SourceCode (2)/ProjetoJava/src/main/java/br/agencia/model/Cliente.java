package br.agencia.model;

public abstract class Cliente {
    protected int idCliente ;
    protected String nome;
    protected String telefone;
    protected String email;

    public Cliente() {

    }

    public Cliente(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public int getIdCliente () {
        return idCliente ;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public abstract String getDocumento();
    public abstract String getNacionalidade();
}


