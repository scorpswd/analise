package org.wilmar.analise.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class VendaTest {

    private static final String ID_VNDA_A = "01";
    private static final String NOME_VNDE_A = "LIONEL RITCHIE";

    private static final String OPEN_BRACKET = "[";
    private static final String CLOSED_BRACKET = "]";

    private static final String HYPHEN = "-";
    private static final String COMMA = ",";

    private static final String ITEM_ID_A = "1";
    private static final String ITEM_QTD_A = "10";
    private static final String ITEM_PRC_A = "100";
    
    private static final String ITEM_ID_B = "2";
    private static final String ITEM_QTD_B = "30";
    private static final String ITEM_PRC_B = "2.50";

    private static final String ITEM_ID_C = "3";
    private static final String ITEM_QTD_C = "40";
    private static final String ITEM_PRC_C = "3.10";
    private static String detalheVndaA;

    private static BigDecimal totA;
    private static BigDecimal totB;
    private static BigDecimal totC;
    private static BigDecimal tot;
    private static BigDecimal notTot;

    private static final String MSG_EX = "Uma exceção do tipo IllegalAccessException deveria ter sido lançada.";

    private static final String MSG_IGUAL = "O valor da venda (%s) deveria ser igual a %s.";
    private static final String MSG_DIFERENTE = "O valor da venda deveria ser diferente de %s.";

    private static Venda venda;

    @BeforeAll
    static void setup() throws IllegalAccessException {
        totA = new BigDecimal(ITEM_QTD_A).multiply(new BigDecimal(ITEM_PRC_A));
        totB = new BigDecimal(ITEM_QTD_B).multiply(new BigDecimal(ITEM_PRC_B));
        totC = new BigDecimal(ITEM_QTD_C).multiply(new BigDecimal(ITEM_PRC_C));

        tot = totA.add(totB).add(totC);
        notTot = totA.add(totB).add(totC).multiply(BigDecimal.TEN);

        detalheVndaA = obtemDetalhe();
        venda = obtemVenda();
    }

    @Test
    void testProcessaVendaIllegalAccess() throws IllegalAccessException {

        assertThrows(IllegalAccessException.class, () -> {
            Venda.processaVenda(ID_VNDA_A, NOME_VNDE_A, null);
        }, MSG_EX);
    }

    @Test
    void testProcessaNotVenda() throws IllegalAccessException {

        assertNotEquals(notTot, venda.getTotalVenda(), 
                obtemMsg(MSG_DIFERENTE, notTot));
    }

    @Test
    void testProcessaVenda() throws IllegalAccessException {

        assertEquals(tot, venda.getTotalVenda(), 
                obtemMsg(MSG_IGUAL, venda.getTotalVenda(), tot));
    }

    private static Venda obtemVenda() throws IllegalAccessException {
        return Venda.processaVenda(ID_VNDA_A, NOME_VNDE_A, detalheVndaA);
    }

    private static String obtemDetalhe() {

        return new StringBuilder()
                .append(OPEN_BRACKET)

                .append(ITEM_ID_A)
                .append(HYPHEN)
                .append(ITEM_QTD_A)
                .append(HYPHEN)
                .append(ITEM_PRC_A)
                .append(COMMA)

                .append(ITEM_ID_B)
                .append(HYPHEN)
                .append(ITEM_QTD_B)
                .append(HYPHEN)
                .append(ITEM_PRC_B)
                .append(COMMA)

                .append(ITEM_ID_C)
                .append(HYPHEN)
                .append(ITEM_QTD_C)
                .append(HYPHEN)
                .append(ITEM_PRC_C)
                .append(CLOSED_BRACKET).toString();
    }

    private static String obtemMsg(final String msgBase, 
            final BigDecimal ...valores) {

        final Object[] strVals = Arrays.stream(valores).map(
                v -> NumberFormat.getCurrencyInstance().format(v)).toArray();

        return String.format(msgBase, strVals);
    }
}