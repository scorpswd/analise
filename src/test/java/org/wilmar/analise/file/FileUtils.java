package org.wilmar.analise.file;

import static org.wilmar.analise.file.FileConstants.NOT_TMP_FILE_SUFFIX;
import static org.wilmar.analise.file.FileConstants.TMP_DIR_PREFFIX;
import static org.wilmar.analise.file.FileConstants.TMP_FILE_PREFFIX;
import static org.wilmar.analise.file.FileConstants.TMP_FILE_SUFFIX;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public final class FileUtils {

    public static final Path createTempDir() throws IOException {
        return Files.createTempDirectory(TMP_DIR_PREFFIX);
    }

    public static final Path createTempDATFile(final Path path) throws IOException {
        return Files.createTempFile(path, TMP_FILE_PREFFIX, TMP_FILE_SUFFIX);
    }

    public static final Path createTempDOTFile(final Path path) throws IOException {
        return Files.createTempFile(path, TMP_FILE_PREFFIX, NOT_TMP_FILE_SUFFIX);
    }

    public static void clear(final Path ...paths) {

        Arrays.stream(paths).forEach(p -> {

            try {
                Files.deleteIfExists(p);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void write(final String resource, final Path target) throws IOException {
        final InputStream stream = FileReaderHelperTest.class.getResourceAsStream(resource);
        Files.copy(stream, target, StandardCopyOption.REPLACE_EXISTING);
        closeQuietly(stream);
    }

    private static void closeQuietly(final InputStream stream) {

        if (stream != null) {

            try {
                stream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}