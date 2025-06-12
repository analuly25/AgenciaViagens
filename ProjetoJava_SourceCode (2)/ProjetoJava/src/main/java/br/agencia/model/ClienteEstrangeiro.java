package br.agencia.model;

public class ClienteEstrangeiro extends Cliente {
    private String passaporte;

    public ClienteEstrangeiro(String nome, String telefone, String email, String passaporte) {
        super(nome, telefone, email);
        this.passaporte = passaporte;
    }

    @Override
    public String getDocumento() {
        return passaporte;
    }

    @Override
    public String getNacionalidade() {
        return "ESTRANGEIRO";
    }
}
