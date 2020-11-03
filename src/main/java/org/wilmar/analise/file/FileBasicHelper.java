package org.wilmar.analise.file;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public final class FileBasicHelper {

    public static final String DAT = ".dat";
    private static final String DONE = ".done";

    private FileBasicHelper() {}

    public static Path obtemDONE(final String outPathStr, final String fName) {

        final String stripped = Optional.ofNullable(
                fName.substring(0, fName.lastIndexOf(FileBasicHelper.DAT))).orElse("");

        final StringBuilder strBldr = new StringBuilder()
                .append(stripped).append(DONE).append(FileBasicHelper.DAT);

        return Paths.get(outPathStr, strBldr.toString());
    }

    public static boolean isDAT(final String fName) {
        return fName.toLowerCase().endsWith(DAT);
    }
}