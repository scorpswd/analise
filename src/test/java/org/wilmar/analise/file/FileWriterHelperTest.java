package org.wilmar.analise.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.wilmar.analise.file.FileConstants.CLIENTE_LIST;
import static org.wilmar.analise.file.FileConstants.VENDA_LIST;
import static org.wilmar.analise.file.FileConstants.VENDEDOR_LIST;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterHelperTest {

    private static final String EXPECTED = "3�3�07鏡ICK ASTLEY";
    private static final String UNEXPECTED = "FIRST.LENGTH";

    private static final int QTD_LINHAS = 1;
    private static final int NOT_QTD_LINHAS = 2;

    private static final int POSICAO_UNICA = 0;

    private static String tmpPathStr;
    private static String fName = "file.done.dat";

    private static Path tmpPath;
    private static Path fPath;

    @BeforeAll
    static void setup() throws IllegalAccessException, IOException {
        tmpPath = FileUtils.createTempDir();
        tmpPathStr = tmpPath.toString();
        fPath = Paths.get(tmpPathStr, fName);
    }

    @AfterEach
    void tearDownEach() {
        FileUtils.clear(fPath);
    }

    @AfterAll
    static void tearDownAll() {
        FileUtils.clear(tmpPath);
    }

    @Test
    void testWriteExiste() {
        FileWriterHelper.write(fPath, VENDEDOR_LIST, CLIENTE_LIST, VENDA_LIST);
        assertTrue(Files.exists(fPath), "O arquivo deveria existir.");
    }

    @Test
    void testWriteNotQtdLinha() throws IOException {
        FileWriterHelper.write(fPath, VENDEDOR_LIST, CLIENTE_LIST, VENDA_LIST);
        final List<String> lines = Files.readAllLines(fPath, StandardCharsets.UTF_8);
        assertNotEquals(NOT_QTD_LINHAS, lines.size(), "O arquivo n鉶 deveria conter mais de uma linha.");
    }

    @Test
    void testWriteQtdLinha() throws IOException {
        FileWriterHelper.write(fPath, VENDEDOR_LIST, CLIENTE_LIST, VENDA_LIST);
        final List<String> lineList = obtemLineList();
        assertEquals(QTD_LINHAS, lineList.size(), "O arquivo deveria conter uma 鷑ica linha.");
    }

    @Test
    void testWriteNotConteudo() throws IOException {
        FileWriterHelper.write(fPath, VENDEDOR_LIST, CLIENTE_LIST, VENDA_LIST);
        final String line = obtemLine();
        assertNotEquals(UNEXPECTED, line, "O conte鷇o do arquivo corresponde a um valor inesperado.");
    }

    @Test
    void testWriteConteudo() throws IOException {
        FileWriterHelper.write(fPath, VENDEDOR_LIST, CLIENTE_LIST, VENDA_LIST);
        final String line = obtemLine();
        assertEquals(EXPECTED, line, "O conte鷇o do arquivo deveria coincidir com o esperado.");
    }

    private static String obtemLine() throws IOException {
        final List<String> lineList = obtemLineList();
        return lineList.get(POSICAO_UNICA);
    }

    private static List<String> obtemLineList() throws IOException {
        return Files.readAllLines(fPath, StandardCharsets.UTF_8);
    }
}