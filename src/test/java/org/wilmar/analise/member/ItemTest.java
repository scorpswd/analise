package org.wilmar.analise.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ItemTest {

    private static final String[] DETALHE_ILLEGAL_ACCESS = {null, ""};
    private static final String DETALHE_ARRAY_INDEX_OOB = "A";

    private static final String ID = "1";
    private static final Integer QUANTIDADE = 5;
    private static final BigDecimal PRECO = new BigDecimal("10.74");
    private static final BigDecimal NOT_PRECO = new BigDecimal("74.10");
    private static BigDecimal total;

    private static Item item;

    @BeforeAll
    static void setup() throws IllegalAccessException {
        item = obtemItem();
        total = new BigDecimal(QUANTIDADE).multiply(PRECO);
    }

    @Test
    void testInstanceIllegalAccess() {

        for (final String detalhe: DETALHE_ILLEGAL_ACCESS) {
            assertThrows(IllegalAccessException.class, () -> {
                Item.instance(detalhe);
            });
        }
    }

    @Test
    void testInstanceArrayOutOfBounds() throws IllegalAccessException {

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Item.instance(DETALHE_ARRAY_INDEX_OOB);
        });
    }

    @Test
    void testInstanceNotPreco() throws IllegalAccessException {
        final Item outroItem = new Item(ID, QUANTIDADE, NOT_PRECO, total);
        assertNotEquals(item, outroItem);
    }

    @Test
    void testInstance() throws IllegalAccessException {
        final Item outroItem = new Item(ID, QUANTIDADE, PRECO, total);
        assertEquals(item, outroItem);
    }

    private static Item obtemItem() throws IllegalAccessException {
        final String detalheStr = obtemDetalheStr();
        return Item.instance(detalheStr);
    }

    private static String obtemDetalheStr() {

        return new StringBuilder()
                .append(ID)
                .append(Item.ITEM_SEPARATOR)
                .append(String.valueOf(QUANTIDADE))
                .append(Item.ITEM_SEPARATOR)
                .append(String.valueOf(PRECO))
                .toString();
    }
}