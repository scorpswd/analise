package org.wilmar.analise.member;

import java.math.BigDecimal;

public class Vendedor {

    public static final String BEAN_NAME = "vendedor";

    private String cpf;

    private String nome;

    private BigDecimal salario;

    public Vendedor() {}

    public Vendedor(final String cpf, final String nome, final BigDecimal salario) {
        super();
        this.cpf = cpf;
        this.nome = nome;
        this.salario = salario;
    }

    public String toString() {
        return String.format("[%s - %s]", this.cpf, this.nome);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
}