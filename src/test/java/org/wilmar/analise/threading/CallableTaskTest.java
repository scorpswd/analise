package org.wilmar.analise.threading;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.wilmar.analise.file.FileUtils;
import org.wilmar.analise.file.data.Result;

class CallableTaskTest {

    private static final String RESOURCE_PATH = "/test/testArq.dat";
    private static final String DONE_FILE = "test.done.dat";

    private static Path tmpReadFile;
    private static Path tmpWriteFile;
    private static Path tmpReadPath;
    private static Path tmpWritePath;

    private static List<Path> pathList;

    @BeforeAll
    static void setUp() throws Exception {
        tmpReadPath = FileUtils.createTempDir();
        tmpReadFile = FileUtils.createTempDATFile(tmpReadPath);
        FileUtils.write(RESOURCE_PATH, tmpReadFile);

        tmpWritePath = FileUtils.createTempDir();
        tmpWriteFile = Paths.get(tmpWritePath.toString(), DONE_FILE);

        pathList = new ArrayList<>(Arrays.asList(tmpWriteFile, tmpWritePath, 
                tmpReadFile, tmpReadPath));
    }

    @AfterAll
    static void tearDown() {
        FileUtils.clear(pathList.toArray(new Path[] {}));
    }

    @AfterEach
    void tearDownEach() {
        FileUtils.clear(tmpWriteFile);
    }

    @Test
    void testCallSucessoIN() {
        final CallableTask callable = new CallableTask(tmpReadFile, tmpWriteFile);
        final Result result = callable.call();
        assertTrue(result.getArqIN().getSucesso());
    }

    @Test
    void testCallSucessoOUT() {
        final CallableTask callable = new CallableTask(tmpReadFile, tmpWriteFile);
        final Result result = callable.call();
        assertTrue(result.getArqOUT().getSucesso());
    }

    @Test
    void testCallFalhaIN() {
        final CallableTask callable = new CallableTask(tmpWriteFile, tmpWriteFile);
        final Result result = callable.call();
        assertFalse(result.getArqIN().getSucesso());
    }

    @Test
    void testCallFalhaOUT() {
        final CallableTask callable = new CallableTask(tmpReadFile, tmpReadFile);
        final Result result = callable.call();
        assertFalse(result.getArqOUT().getSucesso());
    }
}