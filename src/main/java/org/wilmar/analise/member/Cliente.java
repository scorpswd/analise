package org.wilmar.analise.member;

public class Cliente {
    public static final String BEAN_NAME = "cliente";

    private String cnpj;

    private String nome;

    private String negocio;

    public Cliente() {}

    public Cliente(final String cnpj, final String nome, final String negocio) {
        super();
        this.cnpj = cnpj;
        this.nome = nome;
        this.negocio = negocio;
    }

    public String getCnpf() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNegocio() {
        return negocio;
    }

    public void setNegocio(String negocio) {
        this.negocio = negocio;
    }
}