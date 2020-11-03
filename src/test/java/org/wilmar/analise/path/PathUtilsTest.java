package org.wilmar.analise.path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PathUtilsTest {

    private static final int A = 97;
    private static final int Z = 122;
    private static final int STRING_SIZE = 10;

    private static final String USER_HOME = "user.home";
    private static final String HOME_STR = System.getProperty(USER_HOME);
    private static final String DAT = ".DAT";

    private static final String MSG_NAO_CRIADO = "O caminho enviado como parâmetro deveria ter sido criado em disco.";
    private static final String MSG_CRIADO = "O caminho enviado como parâmetro NÃO deveria ter sido criado em disco.";

    private static String inPathStr;
    private static String notInPathStr;

    private static Path inPath;
    private static Path notInPath;

    private static enum PathType {
        NO_SUFFIX, DAT_FILE
    }

    @BeforeAll
    static void setup() throws IOException {
        inPathStr = obtemRandomPath(PathType.NO_SUFFIX);
        inPath = Paths.get(inPathStr);

        notInPathStr = obtemRandomPath(PathType.NO_SUFFIX);
        notInPath = Paths.get(notInPathStr);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.delete(inPath);
    }

    @Test
    void testCriaPaths() throws IOException {
        PathUtils.criaPaths(inPathStr);
        assertTrue(Files.exists(inPath), MSG_NAO_CRIADO);
    }

    @Test
    void testNotCriaPaths() throws IOException {
        PathUtils.criaPaths(inPathStr);
        assertFalse(Files.exists(notInPath), MSG_CRIADO);
    }

    private static String obtemRandomPath(final PathType type) {
        Path randomPath = null;

        while (randomPath == null || Files.exists(randomPath)) {
            final String randomName = obtemRandomString(type);
            randomPath = Paths.get(HOME_STR, randomName);
        }

        return randomPath.toString();
    }

    private static String obtemRandomString(final PathType type) {
        final Random random = new Random();

        final StringBuilder strBldr = new StringBuilder().append(random.ints(A, Z + 1).limit(STRING_SIZE)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString());

        if (PathType.DAT_FILE == type) {
            strBldr.append(DAT);
        }

        return strBldr.toString();
    }
}