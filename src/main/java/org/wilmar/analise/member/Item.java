package org.wilmar.analise.member;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {
    private static final int POS_ID = 0;
    private static final int POS_QUANTIDADE = 1;
    private static final int POS_PRECO = 2;
    public static final String ITEM_SEPARATOR = "-";

    private String id;

    private Integer quantidade;

    private BigDecimal preco;

    private BigDecimal totalItem;

    private Item() {}

    public Item(final String id, final Integer quantidade, final BigDecimal preco, 
            final BigDecimal totalItem) {

        super();
        this.id = id;
        this.quantidade = quantidade;
        this.preco = preco;
        this.totalItem = totalItem;
    }

    public static Item instance(final String detalhe) throws IllegalAccessException {

        if (detalhe == null || "".equals(detalhe.trim())) {
            throw new IllegalAccessException("O detalhamento do item não foi encontrado.");
        }

        final String[] dItem = obtemDetalheItem(detalhe);

        final Integer qtd = obtemQuantidade(dItem);
        final BigDecimal preco = obtemPreco(dItem);
        final BigDecimal totalItem = calculaTotalItem(qtd, preco);
        final String ident = obtemID(dItem);

        return new Item(ident, qtd, preco, totalItem);
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Item oItem = (Item) obj;

        return Objects.equals(this.id, oItem.id) 
                && Objects.equals(this.quantidade, oItem.quantidade)
                && Objects.equals(this.preco, oItem.preco)
                && Objects.equals(this.totalItem, oItem.totalItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.quantidade, this.preco, this.totalItem);
    }

    private static String[] obtemDetalheItem(final String detalhe) {
        return detalhe.split(ITEM_SEPARATOR);
    }

    private static Integer obtemQuantidade(final String ...dItem) {
        return Integer.valueOf(dItem[POS_QUANTIDADE]);
    }

    private static BigDecimal obtemPreco(final String ...dItem) {
        return new BigDecimal(dItem[POS_PRECO]).setScale(2);
    }

    private static String obtemID(final String ...dItem) {
        return dItem[POS_ID];
    }

    private static BigDecimal calculaTotalItem(final Integer qtd, final BigDecimal preco) {
        return new BigDecimal(qtd).multiply(preco);
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(final Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(final BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(final BigDecimal totalItem) {
        this.totalItem = totalItem;
    }
}