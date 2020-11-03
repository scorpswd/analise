package org.wilmar.analise.file;

import com.blackbear.flatworm.ConfigurationReader;
import com.blackbear.flatworm.FileFormat;
import com.blackbear.flatworm.errors.FlatwormConfigurationValueException;
import com.blackbear.flatworm.errors.FlatwormUnsetFieldValueException;


public final class FileFormatHelper {
    private static final String IN_FILE_FORMAT = "/flatworm/in.xml";

    private FileFormatHelper() {}

    public static FileFormat obtemFileFormatIN() throws FlatwormUnsetFieldValueException, FlatwormConfigurationValueException {
        return obtemFileFormat(IN_FILE_FORMAT);
    }

    private static FileFormat obtemFileFormat(final String parm) throws FlatwormUnsetFieldValueException, FlatwormConfigurationValueException {
        final ConfigurationReader parser = new ConfigurationReader();
        return parser.loadConfigurationFile(FileFormatHelper.class.getResourceAsStream(parm));
    }
}