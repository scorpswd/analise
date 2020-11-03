package org.wilmar.analise.threading;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wilmar.analise.file.ArquivoIN;
import org.wilmar.analise.file.ArquivoOUT;
import org.wilmar.analise.file.FileReaderHelper;
import org.wilmar.analise.file.FileWriterHelper;
import org.wilmar.analise.file.data.Result;

public class CallableTask implements java.util.concurrent.Callable<Result> {

    private static final Logger LOG = LogManager.getLogger(CallableTask.class);
    private final Path inPath;
    private final Path outPath;

    public CallableTask(final Path inPath, final Path outPath) {
        this.inPath = inPath;
        this.outPath = outPath;
    }

    @Override
    public Result call() {
        final Instant ini = Instant.now();
        ArquivoIN arqIN = null;
        ArquivoOUT arqOut = null;

        if (Files.exists(this.outPath)) {
            LOG.info("Arquivo de resposta de {} encontrado ANTES do início do processamento. Nenhuma ação será executada.", this.inPath);
            arqIN = ArquivoIN.fail();
            arqOut = ArquivoOUT.fail();

        } else if (Files.exists(this.inPath)) {
            arqIN = FileReaderHelper.read(this.inPath);
            LOG.info("{} foi lido com {}.", this.inPath, arqIN.getSucesso() ? "sucesso" : "falha");

            if (arqIN.getSucesso()) {

                arqOut = FileWriterHelper.write(this.outPath, 
                        arqIN.getVendedorList(), arqIN.getClienteList(), arqIN.getVendaList());
            }

        } else {
            arqIN = ArquivoIN.fail();
            arqOut = ArquivoOUT.fail();
        }

        final Instant end = Instant.now();
        LOG.info("Duração do processamento de {}: {} milissegundos.", this.inPath, Duration.between(ini, end).toMillis());
        return new Result(arqIN, arqOut);
    }
}