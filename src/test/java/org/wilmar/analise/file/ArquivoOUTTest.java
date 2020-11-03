package org.wilmar.analise.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.wilmar.analise.file.FileConstants.CLIENTE_LIST;
import static org.wilmar.analise.file.FileConstants.ID_VENDA_A;
import static org.wilmar.analise.file.FileConstants.ID_VENDA_B;
import static org.wilmar.analise.file.FileConstants.ID_VENDA_C;
import static org.wilmar.analise.file.FileConstants.VENDA_LIST;
import static org.wilmar.analise.file.FileConstants.VENDEDOR_A;
import static org.wilmar.analise.file.FileConstants.VENDEDOR_B;
import static org.wilmar.analise.file.FileConstants.VENDEDOR_C;
import static org.wilmar.analise.file.FileConstants.VENDEDOR_LIST;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ArquivoOUTTest {

    private static final String MSG_IAE = "Uma exceção do tipo IllegalAccessException deveria ter sido lançada.";

    private static final String MSG_MAIOR_VENDA = "O identificador da maior venda";
    private static final String MSG_PIOR_VENDEDOR = "A identificação do pior vendedor";
    private static final String MSG_QTD_CLIENTE = "A quantidade de clientes";
    private static final String MSG_QTD_VENDEDOR = "A quantidade de vendedores";
    private static final String MSG_IGUAL = "(%s) deveria ser igual a %s.";
    private static final String MSG_DIFERENTE = "deveria ser diferente de %s.";

    private static ArquivoOUT arqOUT;

    @BeforeAll
    static void setup() throws IllegalAccessException {
        arqOUT = obtemArquivoOUT();
    }

    @Test
    void testProcessaClienteVazio() throws IllegalAccessException {

        assertThrows(IllegalAccessException.class, () -> {
            ArquivoOUT.processa(new ArrayList<>(), new ArrayList<>(), 
                    new ArrayList<>());
        }, MSG_IAE);
    }

    @Test
    void testProcessaVendaVazia() throws IllegalAccessException {

        assertThrows(IllegalAccessException.class, () -> {
            ArquivoOUT.processa(VENDEDOR_LIST, CLIENTE_LIST, new ArrayList<>());
        }, MSG_IAE);
    }

    @Test
    void testProcessaVendedorVazio() throws IllegalAccessException {

        assertThrows(IllegalAccessException.class, () -> {
            ArquivoOUT.processa(new ArrayList<>(), CLIENTE_LIST, VENDA_LIST);
        }, MSG_IAE);
    }

    @Test
    void testProcessaNotMaiorVendaA() throws IllegalAccessException {

        assertNotEquals(ID_VENDA_A, arqOUT.getMaiorVenda().getId(), 
                obtemMsg(MSG_MAIOR_VENDA, MSG_DIFERENTE, ID_VENDA_A));
    }

    @Test
    void testProcessaNotMaiorVendaB() throws IllegalAccessException {

        assertNotEquals(ID_VENDA_B, arqOUT.getMaiorVenda().getId(), 
                obtemMsg(MSG_MAIOR_VENDA, MSG_DIFERENTE, ID_VENDA_B));
    }

    @Test
    void testProcessaMaiorVenda() throws IllegalAccessException {

        assertEquals(ID_VENDA_C, arqOUT.getMaiorVenda().getId(), 
                obtemMsg(MSG_MAIOR_VENDA, MSG_IGUAL, 
                        arqOUT.getMaiorVenda().getId(), ID_VENDA_C));
    }

    @Test
    void testProcessaNOTPiorVendedorA() throws IllegalAccessException {

        assertNotEquals(VENDEDOR_A, arqOUT.getPiorVendedor(), 
                obtemMsg(MSG_PIOR_VENDEDOR, MSG_DIFERENTE, VENDEDOR_A));
    }

    @Test
    void testNOTProcessaNOTPiorVendedorC() throws IllegalAccessException {

        assertNotEquals(VENDEDOR_C, arqOUT.getPiorVendedor(), 
                obtemMsg(MSG_PIOR_VENDEDOR, MSG_DIFERENTE, VENDEDOR_C));
    }

    @Test
    void testProcessaPiorVendedor() throws IllegalAccessException {

        assertEquals(VENDEDOR_B, arqOUT.getPiorVendedor(), 
                obtemMsg(MSG_PIOR_VENDEDOR, MSG_IGUAL, 
                        arqOUT.getPiorVendedor(), VENDEDOR_B));
    }

    @Test
    void testProcessaNOTQtdCliente() {
        final int qtd = CLIENTE_LIST.size() + VENDA_LIST.size();

        assertNotEquals(qtd, arqOUT.getQtdCliente(), 
                obtemMsg(MSG_QTD_CLIENTE, MSG_DIFERENTE, 
                        arqOUT.getQtdCliente()));
    }

    @Test
    void testProcessaQtdCliente() {

        assertEquals(CLIENTE_LIST.size(), arqOUT.getQtdCliente(), 
                obtemMsg(MSG_QTD_CLIENTE, MSG_IGUAL, 
                        arqOUT.getQtdCliente(), CLIENTE_LIST.size()));
    }

    @Test
    void testProcessaNOTQtdVendedor() {

        assertNotEquals(VENDEDOR_LIST.size() + CLIENTE_LIST.size(), 
                arqOUT.getQtdVendedor(), obtemMsg(MSG_QTD_VENDEDOR, 
                        MSG_DIFERENTE, arqOUT.getQtdVendedor()));
    }

    @Test
    void testProcessaQtdVendedor() {

        assertEquals(VENDEDOR_LIST.size(), arqOUT.getQtdVendedor(), 
                obtemMsg(MSG_QTD_VENDEDOR, MSG_IGUAL, 
                        arqOUT.getQtdVendedor(), VENDEDOR_LIST.size()));
    }

    private static ArquivoOUT obtemArquivoOUT() throws IllegalAccessException {
        return ArquivoOUT.processa(VENDEDOR_LIST, CLIENTE_LIST, VENDA_LIST);
    }

    private static String obtemMsg(final String preffix, final String suffix, final Object ...valores) {

        final String msgBase = new StringBuilder()
                .append(preffix)
                .append(" ")
                .append(suffix)
                .toString();

        return String.format(msgBase, valores);
    }
}