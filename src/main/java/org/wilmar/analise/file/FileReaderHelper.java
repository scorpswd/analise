package org.wilmar.analise.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wilmar.analise.member.Cliente;
import org.wilmar.analise.member.Venda;
import org.wilmar.analise.member.Vendedor;

import com.blackbear.flatworm.FileFormat;
import com.blackbear.flatworm.MatchedRecord;

public final class FileReaderHelper {
    private static final Logger LOG = LogManager.getLogger(FileReaderHelper.class);

    private static final int RETRIES = 10_000;
    private static final int WAIT_TIME_IN_MILLIS = 15;

    private FileReaderHelper() {
        super();
    }

    public static String[] obtemExistentes(final String inPathStr) {
        LOG.info("Procurando arquivos existentes em {}.", inPathStr);
        final FilenameFilter filter = (dir, name) -> name.endsWith(FileBasicHelper.DAT);

        final File dir = new File(inPathStr);
        final String[] files = dir.list(filter);
        LOG.info("Arquivos encontrados: {}.", Arrays.toString(files));

        return files;
    }

    public static ArquivoIN read(final Path path) {
        boolean sucesso = true;
        final List<Vendedor> vendedorList = new ArrayList<>();
        final List<Cliente> clienteList = new ArrayList<>();
        final List<Venda> vendaList = new ArrayList<>();

        BufferedReader bufIn = null;

        try {
            wait(path);
            bufIn = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            MatchedRecord record;
            final FileFormat format = FileFormatHelper.obtemFileFormatIN();            

            while ((record = format.getNextRecord(bufIn)) != null) {

                switch (record.getRecordName()) {

                case Vendedor.BEAN_NAME:
                    final Vendedor vendedor = (Vendedor) record.getBean(Vendedor.BEAN_NAME);
                    vendedorList.add(vendedor);
                    break;

                case Cliente.BEAN_NAME:
                    clienteList.add((Cliente) record.getBean(Cliente.BEAN_NAME));
                    break;

                case Venda.BEAN_NAME:
                    final Venda venda = (Venda) record.getBean(Venda.BEAN_NAME);
                    vendaList.add(venda);
                    break;

                default:
                    break;
                }
            }

        } catch (final Exception e) {
            LOG.error(e);
            sucesso = false;
        }

        if (vendedorList.isEmpty() && clienteList.isEmpty() && vendaList.isEmpty()) {
            sucesso = false;
            LOG.info("O arquivo {} não contém registros válidos.", path);
        }

        closeQuietly(bufIn);
        return new ArquivoIN(vendedorList, clienteList, vendaList, sucesso);
    }

    private static boolean closeQuietly(final BufferedReader bufIn) {
        boolean result = false;

        if (bufIn != null) {

            try {
                bufIn.close();
                result = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result; 
    }

    private static void wait(final Path path) throws InterruptedException {
        int retry = 1;
        LOG.info("Verificando viabilidade de acesso ao arquivo {}.", path);

        while (retry < RETRIES && !Files.isReadable(path)) {
            LOG.info("O arquivo {} pode estar em uso por outro processo. [tentativa {} de {}].", path, retry, RETRIES);
            Thread.sleep(WAIT_TIME_IN_MILLIS);
            retry++;
        }

        if (retry == RETRIES) {
            LOG.info("Tempo de espera por acesso ao arquivo {} foi esgotado.", path);

        } else {
            LOG.info("O arquivo {} é acessível.", path);
        }
    }
}