package org.wilmar.analise.threading;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.wilmar.analise.file.FileBasicHelper;

public final class ExecutorHelper {
    private static final int THREADS = 30;
    private static final ExecutorService EXEC = Executors.newFixedThreadPool(THREADS);

    private ExecutorHelper() {}

    public static void submete(final String inPathStr, final String outPathStr, final String fName) {
        final Path donePath = FileBasicHelper.obtemDONE(outPathStr, fName);
        EXEC.submit(new CallableTask(Paths.get(inPathStr, fName), donePath));
    }

    public static void submete(final String inPathStr, final String outPathStr, final String[] fNames) {

        if (fNames != null) {

            for (final String fName : fNames) {
                submete(inPathStr, outPathStr, fName);
            }
        }
    }
}