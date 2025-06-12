package br.agencia.model;

public class ClienteNacional extends Cliente {
    private String cpf;

    public ClienteNacional(String nome, String telefone, String email, String cpf) {
        super(nome, telefone, email);
        this.cpf = cpf;
    }

    @Override
    public String getDocumento() {
        return cpf;
    }

    @Override
    public String getNacionalidade() {
        return "NACIONAL";
    }
}