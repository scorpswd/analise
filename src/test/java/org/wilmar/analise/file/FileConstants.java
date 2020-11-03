package org.wilmar.analise.file;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.wilmar.analise.member.Cliente;
import org.wilmar.analise.member.Venda;
import org.wilmar.analise.member.Vendedor;

public final class FileConstants {

    public static final String CPF_VENDEDOR_A = "14855161020";
    public static final String NOME_VENDEDOR_A = "LIONEL RITCHIE";
    public static final BigDecimal SALARIO_VENDEDOR_A = new BigDecimal("9999.99");

    public static final String CPF_VENDEDOR_B = "23241871088";
    public static final String NOME_VENDEDOR_B = "RICK ASTLEY";
    public static final BigDecimal SALARIO_VENDEDOR_B = new BigDecimal("7200.41");

    public static final String CPF_VENDEDOR_C = "74609244071";
    public static final String NOME_VENDEDOR_C = "STEVE PERRY";
    public static final BigDecimal SALARIO_VENDEDOR_C = new BigDecimal("15200.83");

    public static final String CNPJ_CLIENTE_A = "04315462000159";
    public static final String NOME_CLIENTE_A = "COMMODORES LLC"; 
    public static final String AREA_CLIENTE_A = "MUSIC";

    public static final String CNPJ_CLIENTE_B = "15022856000135";
    public static final String NOME_CLIENTE_B = "LAUGH CO";
    public static final String AREA_CLIENTE_B = "COMEDY";

    public static final String CNPJ_CLIENTE_C = "77699830000183";
    public static final String NOME_CLIENTE_C = "MOVIE LTD";
    public static final String AREA_CLIENTE_C = "FILM";

    public static final String ID_VENDA_A = "01";
    public static final String DETALHE_VENDA_A = "[1-10-100,2-30-2.50,3-40-3.10]";

    public static final String ID_VENDA_B = "88";
    public static final String DETALHE_VENDA_B = "[2-7-49,33-25-2.50,9-65-9.99]";

    public static final String ID_VENDA_C = "07";
    public static final String DETALHE_VENDA_C = "[4-4-96,1-1-9.45,38-952-2.75]";

    public static final Vendedor VENDEDOR_A = new Vendedor(CPF_VENDEDOR_A, NOME_VENDEDOR_A, SALARIO_VENDEDOR_A);
    public static final Vendedor VENDEDOR_B = new Vendedor(CPF_VENDEDOR_B, NOME_VENDEDOR_B, SALARIO_VENDEDOR_B);
    public static final Vendedor VENDEDOR_C = new Vendedor(CPF_VENDEDOR_C, NOME_VENDEDOR_C, SALARIO_VENDEDOR_C);
    public static final List<Vendedor> VENDEDOR_LIST = Arrays.asList(VENDEDOR_A, VENDEDOR_B, VENDEDOR_C);

    public static final Cliente CLIENTE_A = new Cliente(CNPJ_CLIENTE_A, NOME_CLIENTE_A, AREA_CLIENTE_A);
    public static final Cliente CLIENTE_B = new Cliente(CNPJ_CLIENTE_B, NOME_CLIENTE_B, AREA_CLIENTE_B);
    public static final Cliente CLIENTE_C = new Cliente(CNPJ_CLIENTE_C, NOME_CLIENTE_C, AREA_CLIENTE_C);
    public static final List<Cliente> CLIENTE_LIST = Arrays.asList(CLIENTE_A, CLIENTE_B, CLIENTE_C);

    public static final Venda VENDA_A = new Venda(ID_VENDA_A, NOME_VENDEDOR_A, DETALHE_VENDA_A);
    public static final Venda VENDA_B = new Venda(ID_VENDA_B, NOME_VENDEDOR_B, DETALHE_VENDA_B);
    public static final Venda VENDA_C = new Venda(ID_VENDA_C, NOME_VENDEDOR_C, DETALHE_VENDA_C);
    public static final List<Venda> VENDA_LIST = Arrays.asList(VENDA_A, VENDA_B, VENDA_C);

    public static final String TMP_DIR_PREFFIX = "analiseTestDir";
    public static final String TMP_FILE_PREFFIX = "analiseTestFile";
    public static final String TMP_FILE_SUFFIX = ".dat";
    public static final String NOT_TMP_FILE_SUFFIX = ".dot";

    private FileConstants() {}
}