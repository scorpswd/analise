package org.wilmar.analise.member;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    public static final String BEAN_NAME = "venda";

    private static final String REGEX = "[\\p{Ps}\\p{Pe}]";
    private static final String DETALHE_SEPARATOR = ",";

    private String id;

    private String detalhe;

    private String vendedor;

    private BigDecimal totalVenda = BigDecimal.ZERO;

    private List<Item> itemList = new ArrayList<>();

    public Venda() {}

    public Venda(final String id, final String vendedor, final String detalhe) { 
        super();
        this.id = id;
        this.vendedor = vendedor;
        this.detalhe = detalhe;
    }

    private static String[] obtemDetalhes(final String detalhe) {
        return detalhe.replaceAll(REGEX, "").split(DETALHE_SEPARATOR);
    }

    public static Venda processaVenda(final String id, final String vendedor, 
            final String detalhe) throws IllegalAccessException {

        final Venda venda = new Venda(id, vendedor, detalhe);
        return venda.processaVenda();
    }

    public Venda processaVenda() throws IllegalAccessException {

        if (this.detalhe == null || "".equals(this.detalhe.trim())) {
            throw new IllegalAccessException("O detalhamento de vendas não foi encontrado.");
        }

        this.itemList = new ArrayList<>();
        this.totalVenda = BigDecimal.ZERO;

        final String[] detalhes = obtemDetalhes(this.detalhe);

        for (final String det : detalhes) {
            final Item item = Item.instance(det);
            this.itemList.add(item);
            this.somaVenda(item.getTotalItem());
        }

        return this;
    }

    private void somaVenda(final BigDecimal totalItem) {
        this.totalVenda = this.totalVenda.add(totalItem);
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(final String detalhe) {
        this.detalhe = detalhe;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(final String vendedor) {
        this.vendedor = vendedor;
    }

    public BigDecimal getTotalVenda() {
        return totalVenda;
    }

    public List<Item> getItemList() {
        return itemList;
    }
}