package org.wilmar.analise.file;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.wilmar.analise.member.Cliente;
import org.wilmar.analise.member.Venda;
import org.wilmar.analise.member.Vendedor;

public class ArquivoOUT {

    private Integer qtdCliente;
    private Integer qtdVendedor;

    private Path path;

    private Boolean sucesso = Boolean.FALSE;

    private Venda maiorVenda;
    private Vendedor piorVendedor;

    private List<Vendedor> vendedorList = new ArrayList<>();
    private List<Cliente> clienteList = new ArrayList<>();
    private List<Venda> vendaList = new ArrayList<>();

    public ArquivoOUT() {}

    public ArquivoOUT(final List<Vendedor> vendedorList, final List<Cliente> clienteList, 
            final List<Venda> vendaList) {
        super();
        this.vendedorList = vendedorList;
        this.clienteList = clienteList;
        this.vendaList = vendaList;
    }

    public ArquivoOUT(final List<Vendedor> vendedorList, final List<Cliente> clienteList, 
            final List<Venda> vendaList, final Path path) {

        super();
        this.vendedorList = vendedorList;
        this.clienteList = clienteList;
        this.vendaList = vendaList;
        this.path = path;
    }

    public static ArquivoOUT fail() {
        final ArquivoOUT arqOUT = new ArquivoOUT();
        arqOUT.setSucesso(Boolean.FALSE);
        return arqOUT;
    }

    private Venda processaMaiorVenda(final List<Venda> vendaProcList) {
        this.maiorVenda = Collections.max(vendaProcList, Comparator.comparing(v -> v.getTotalVenda()));
        return maiorVenda;
    }

    private Vendedor processaPiorVendedor(final List<Venda> vendaProcList) throws IllegalAccessException {

        if (this.vendedorList == null || this.vendedorList.isEmpty()) {
            throw new IllegalAccessException("A relação de vendedores não foi encontrada.");
        }

        final Map<String, BigDecimal> map = vendaProcList.stream().collect(
                Collectors.groupingBy(Venda::getVendedor, 
                        Collectors.reducing(BigDecimal.ZERO, 
                                Venda::getTotalVenda, BigDecimal::add)));

        final String piorNome = Collections.min(map.entrySet(), COMP).getKey();
        this.piorVendedor = this.vendedorList.stream().filter(v -> v.getNome().equals(piorNome)).findFirst().orElse(null);
        return this.piorVendedor;
    }

    public static ArquivoOUT processa(final List<Vendedor> vendedorList, final List<Cliente> clienteList, 
            final List<Venda> vendaList) throws IllegalAccessException {
        return new ArquivoOUT(vendedorList, clienteList, vendaList).processa();
    }

    private ArquivoOUT processa() throws IllegalAccessException {

        if (this.clienteList == null || this.clienteList.isEmpty()) {
            throw new IllegalAccessException("A relação de clientes não foi encontrada.");
        }

        if (this.vendaList == null || this.vendaList.isEmpty()) {
            throw new IllegalAccessException("A relação de vendas não foi encontrada.");
        }

        final List<Venda> vendaProcList = this.obtemVendaProcList();

        this.processaMaiorVenda(vendaProcList);
        this.processaPiorVendedor(vendaProcList);

        this.qtdCliente = this.clienteList.size();
        this.qtdVendedor = this.vendedorList.size();
        return this;
    }

    private List<Venda> obtemVendaProcList() throws IllegalAccessException {
        final List<Venda> vendaProcList = new ArrayList<>();

        for (final Venda venda : this.vendaList) {

            final Venda vendaProc = Venda.processaVenda(venda.getId(), 
                    venda.getVendedor(), venda.getDetalhe());

            vendaProcList.add(vendaProc);
        }

        return vendaProcList;
    }

    private static final Comparator<Entry<String, BigDecimal>> COMP = new Comparator<Map.Entry<String,BigDecimal>>() {

        @Override
        public int compare(final Entry<String, BigDecimal> entryA, 
                final Entry<String, BigDecimal> entryB) {

            return entryA.getValue().compareTo(entryB.getValue());
        }
    };

    public Integer getQtdCliente() {
        return qtdCliente;
    }

    public void setQtdCliente(final Integer qtdCliente) {
        this.qtdCliente = qtdCliente;
    }

    public Integer getQtdVendedor() {
        return qtdVendedor;
    }

    public void setQtdVendedor(final Integer qtdVendedor) {
        this.qtdVendedor = qtdVendedor;
    }

    public Venda getMaiorVenda() {
        return maiorVenda;
    }

    public void setMaiorVenda(final Venda maiorVenda) {
        this.maiorVenda = maiorVenda;
    }

    public Vendedor getPiorVendedor() {
        return piorVendedor;
    }

    public void setPiorVendedor(final Vendedor piorVendedor) {
        this.piorVendedor = piorVendedor;
    }

    public Boolean getSucesso() {
        return sucesso;
    }

    public void setSucesso(final Boolean sucesso) {
        this.sucesso = sucesso;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(final Path path) {
        this.path = path;
    }
 }