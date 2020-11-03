package org.wilmar.analise.path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PathUtils {

    private static final Logger LOG = LogManager.getLogger(PathUtils.class);

    private static final String USER_HOME = "user.home";
    private static final String HOME_STR = System.getProperty(USER_HOME);
    private static final String DATA = "data";

    private static final String IN = "in";
    private static final Path IN_PATH = obtemPath(IN);
    public static final String IN_PATH_STR = IN_PATH.toString();

    private static final String OUT = "out";
    private static final Path OUT_PATH = obtemPath(OUT);
    public static final String OUT_PATH_STR = OUT_PATH.toString();

    private PathUtils() {}

    private static Path obtemPath(final String fim) {
        final Path path = Paths.get(HOME_STR, DATA, fim);
        LOG.info("path ({}): {}", fim, path);
        return path;
    }

    public static void criaPaths(final String ...paths) throws IOException {

        for (final String pathStr : paths) {
            final Path path = Paths.get(pathStr);
    
            if (!Files.exists(path)) {
                LOG.info("O caminho {} não existe. A estrutura será criada.", path);
                Files.createDirectories(path);
                LOG.info("O caminho {} foi criado.", path);
            }
        }
    }
}