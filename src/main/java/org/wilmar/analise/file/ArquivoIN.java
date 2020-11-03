package org.wilmar.analise.file;

import java.util.ArrayList;
import java.util.List;

import org.wilmar.analise.member.Cliente;
import org.wilmar.analise.member.Venda;
import org.wilmar.analise.member.Vendedor;

public final class ArquivoIN {

    private Boolean sucesso = Boolean.FALSE;

    private List<Vendedor> vendedorList = new ArrayList<>();
    private List<Cliente> clienteList = new ArrayList<>();
    private List<Venda> vendaList = new ArrayList<>();

    private ArquivoIN() {}

    public ArquivoIN(final List<Vendedor> vendedorList, final List<Cliente> clienteList, 
            final List<Venda> vendaList, final Boolean sucesso) {

        super();
        this.vendedorList = vendedorList;
        this.clienteList = clienteList;
        this.vendaList = vendaList;
        this.sucesso = sucesso;
    }

    public static ArquivoIN fail() {
        final ArquivoIN arqIN = new ArquivoIN();
        arqIN.setSucesso(Boolean.FALSE);
        return arqIN;
    }

    public Boolean getSucesso() {
        return sucesso;
    }

    public void setSucesso(final Boolean sucesso) {
        this.sucesso = sucesso;
    }

    public List<Vendedor> getVendedorList() {
        return vendedorList;
    }

    public void setVendedorList(final List<Vendedor> vendedorList) {
        this.vendedorList = vendedorList;
    }

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(final List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public List<Venda> getVendaList() {
        return vendaList;
    }

    public void setVendaList(final List<Venda> vendaList) {
        this.vendaList = vendaList;
    }
}