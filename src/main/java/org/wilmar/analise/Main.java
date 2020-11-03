package org.wilmar.analise;

import java.io.IOException;

import org.wilmar.analise.file.FileReaderHelper;
import org.wilmar.analise.path.PathUtils;
import org.wilmar.analise.threading.ExecutorHelper;
import org.wilmar.analise.watcher.WatcherHelper;

public final class Main {

    private Main() {}

    private static void processaExistentes(final String inPathStr, final String outPathStr) {

        try {
            final String[] files = FileReaderHelper.obtemExistentes(inPathStr);
            ExecutorHelper.submete(inPathStr, outPathStr, files);

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private static void processaNovos(final String inPathStr, final String outPathStr) {

        try {
            WatcherHelper.execute(inPathStr, outPathStr);

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) throws IOException  {
        PathUtils.criaPaths(PathUtils.IN_PATH_STR, PathUtils.OUT_PATH_STR);

        processaExistentes(PathUtils.IN_PATH_STR, PathUtils.OUT_PATH_STR);
        processaNovos(PathUtils.IN_PATH_STR, PathUtils.OUT_PATH_STR);
    }
}