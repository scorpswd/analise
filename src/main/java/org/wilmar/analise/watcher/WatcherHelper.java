package org.wilmar.analise.watcher;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wilmar.analise.file.FileBasicHelper;
import org.wilmar.analise.threading.ExecutorHelper;

public final class WatcherHelper {

    private static final Logger LOG = LogManager.getLogger(WatcherHelper.class);

    private WatcherHelper() {}

    private static WatchService createWatcher(final Path inPath) throws IOException {
        LOG.info("Criando watcher.");
        final FileSystem fSys = inPath.getFileSystem();
        final WatchService watcher = fSys.newWatchService();
        LOG.info("Watcher criado.");
        return watcher;
    }

    public static void execute(final String inPathStr, final String outPathStr) throws IOException, InterruptedException {
        final Path inPath = Paths.get(inPathStr);
        final WatchService watcher = createWatcher(inPath);
        inPath.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

        WatchKey key;

        while ((key = watcher.take()) != null) {

            for (final WatchEvent<?> event : key.pollEvents()) {
                final String fName = event.context().toString();

                if (FileBasicHelper.isDAT(fName) && StandardWatchEventKinds.ENTRY_CREATE == event.kind()) {
                    ExecutorHelper.submete(inPathStr, outPathStr, fName);
                }
            }

            key.reset();
        }
    }
}