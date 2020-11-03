package org.wilmar.analise.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class FileReaderHelperTest {

    private static final String RESOURCE_PATH = "/test/testArq.dat";
    private static final String NOT_RESOURCE_PATH = "/test/testGibberish.dat";

    private static final int ZERO = 0;
    private static final int UM = 1;
    private static final int DOIS = 2;

    private static final String MSG_NENHUM = "Não deveria existir nenhum arquivo na pasta.";
    private static final String MSG_UM = "Deveria existir um único arquivo na pasta.";
    private static final String MSG_DOIS = "Deveria existir apenas dois arquivos na pasta.";
    private static final String MSG_FALHA = "O arquivo deveria ter sido lido com falha.";
    private static final String MSG_SUCESSO = "O arquivo deveria ter sido lido com sucesso.";

    private static Path tmpExistPath;
    private static String tmpExistPathStr;

    private static Path tmpReadPath;
    private static Path tmpReadFile;
    private static Path tmpNotReadFile;

    private static List<Path> pathList;

    @BeforeAll
    static void setup() throws IOException {
        tmpExistPath = FileUtils.createTempDir();
        tmpExistPathStr = tmpExistPath.toString();
        pathList = new ArrayList<>();

        tmpReadPath = FileUtils.createTempDir();

        tmpReadFile = FileUtils.createTempDATFile(tmpReadPath);
        FileUtils.write(RESOURCE_PATH, tmpReadFile);

        tmpNotReadFile = FileUtils.createTempDATFile(tmpReadPath);
        FileUtils.write(NOT_RESOURCE_PATH, tmpNotReadFile);
    }

    @AfterAll
    static void tearDown() {

        pathList.addAll(new ArrayList<Path>(
                Arrays.asList(tmpReadFile, tmpNotReadFile, tmpReadPath, tmpExistPath)));

        FileUtils.clear(pathList.toArray(new Path[] {}));
    }

    @Test
    @Order(1)
    void testObtemExistentesZEROA() throws IOException {
        final String[] first = FileReaderHelper.obtemExistentes(tmpExistPathStr);
        assertEquals(ZERO, first.length, MSG_NENHUM);
    }

    @Test
    @Order(2)
    void testObtemExistentesZEROB() throws IOException {
        final Path file = FileUtils.createTempDOTFile(tmpExistPath);
        pathList.add(file);
        final String[] second = FileReaderHelper.obtemExistentes(tmpExistPathStr);
        assertEquals(ZERO, second.length, MSG_NENHUM);
    }

    @Test
    @Order(3)
    void testObtemExistentesUM() throws IOException {
        final Path file = FileUtils.createTempDATFile(tmpExistPath);
        pathList.add(file);
        final String[] third = FileReaderHelper.obtemExistentes(tmpExistPathStr);
        assertEquals(UM, third.length, MSG_UM);
    }

    @Test
    @Order(4)
    void testObtemExistentesDOISA() throws IOException {
        final Path file = FileUtils.createTempDATFile(tmpExistPath);
        pathList.add(file);
        final String[] fourth = FileReaderHelper.obtemExistentes(tmpExistPathStr);
        assertEquals(DOIS, fourth.length, MSG_DOIS);
    }

    @Test
    @Order(5)
    void testObtemExistentesDOISB() throws IOException {
        final Path file = FileUtils.createTempDOTFile(tmpExistPath);
        pathList.add(file);
        final String[] fifth = FileReaderHelper.obtemExistentes(tmpExistPathStr);
        assertEquals(DOIS, fifth.length, MSG_DOIS);
    }

    @Test
    void testNotRead() {
        final ArquivoIN arqIN = FileReaderHelper.read(tmpNotReadFile);
        assertFalse(arqIN.getSucesso(), MSG_FALHA);
    }

    @Test
    void testRead() {
        final ArquivoIN arqIN = FileReaderHelper.read(tmpReadFile);
        assertTrue(arqIN.getSucesso(), MSG_SUCESSO);
    }
}