package org.wilmar.analise.file;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wilmar.analise.member.Cliente;
import org.wilmar.analise.member.Venda;
import org.wilmar.analise.member.Vendedor;

import com.blackbear.flatworm.FileCreator;
import com.blackbear.flatworm.errors.FlatwormCreatorException;

public final class FileWriterHelper {
    private static final Logger LOG = LogManager.getLogger(FileWriterHelper.class);

    private static final String OUT_FILE_FORMAT = "/flatworm/out.xml";
    private static final String OUT = "out";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private FileWriterHelper() {
        super();
    }

    public static ArquivoOUT write(final Path path, final List<Vendedor> vendedorList, 
            final List<Cliente> clienteList, final List<Venda> vendaList) {

        boolean sucesso = true;
        ArquivoOUT arqOUT = null;

        try {
            LOG.info("Processando conteúdo do arquivo de retorno {}.", path);
            arqOUT = ArquivoOUT.processa(vendedorList, clienteList, vendaList);
            LOG.info("Iniciando escrita do arquivo de retorno {}.", path);
            flatwormWrite(path.toString(), arqOUT);

        } catch (final IllegalAccessException | FlatwormCreatorException | IOException e) {
            e.printStackTrace();
            sucesso = false;
        }

        if (arqOUT == null) {
            arqOUT = new ArquivoOUT();

        } else {
            arqOUT.setSucesso(sucesso);
        }

        arqOUT.setPath(path);
        LOG.info("Escrita do arquivo de retorno {} finalizada com {}.", path, sucesso ? "sucesso" : "falha");
        return arqOUT;
    }

    private static void flatwormWrite(final String path, 
            final ArquivoOUT arqOUT) throws FlatwormCreatorException, IOException {

        FileCreator creator = null;
        InputStream inputStream = null; 

        try {
            inputStream = FileWriterHelper.class.getResourceAsStream(OUT_FILE_FORMAT);
            creator = new FileCreator(inputStream, path);
            creator.setRecordSeperator(LINE_SEPARATOR);
            creator.open();
            creator.setBean(OUT, arqOUT);
            creator.write(OUT);

        } finally {

            if (creator != null) {

                try {
                    creator.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}