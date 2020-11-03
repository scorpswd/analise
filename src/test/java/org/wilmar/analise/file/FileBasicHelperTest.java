package org.wilmar.analise.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileBasicHelperTest {

    private static final String DAT_FILE_NAME = "fTest.dat";
    private static final String NOT_DAT_FILE_NAME = "fTest.7z";
    private static final String HOME = System.getProperty("user.home");
    private static final String DATA = "data";
    private static final String OUT = "out";
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    private static final String DONE_FILE_NAME = "fTest.done.dat";
    private static final Path DAT_TEST_PATH = Paths.get(HOME, DATA, OUT, DAT_FILE_NAME);
    private static final Path DONE_TEST_PATH = Paths.get(HOME, DATA, OUT, DONE_FILE_NAME);

    private static String outPathStr;
    private static Path path;

    @BeforeAll
    static void setup() {
        outPathStr = obtemOutPathStr();
        path = FileBasicHelper.obtemDONE(outPathStr, DAT_FILE_NAME);
    }

    @Test
    void testNOTObtemDone() {
        assertNotEquals(DAT_TEST_PATH, path, 
                "O arquivo não deveria ser do tipo .DONE");
    }

    @Test
    void testObtemDone() {
        assertEquals(DONE_TEST_PATH, path, 
                "O arquivo deveria ser do tipo .DONE");
    }

    @Test
    void testIsNOTDAT() {
        assertFalse(FileBasicHelper.isDAT(NOT_DAT_FILE_NAME), 
                "O arquivo não deveria ser do tipo .DAT");
    }

    @Test
    void testIsDAT() {
        assertTrue(FileBasicHelper.isDAT(DAT_FILE_NAME), 
                "O arquivo deveria ser do tipo .DAT");
    }

    private static String obtemOutPathStr() {

        return new StringBuilder()
                .append(HOME)
                .append(FILE_SEPARATOR)
                .append(DATA)
                .append(FILE_SEPARATOR)
                .append(OUT).toString();
    }
}