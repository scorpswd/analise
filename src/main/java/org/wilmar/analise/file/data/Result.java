package org.wilmar.analise.file.data;

import org.wilmar.analise.file.ArquivoIN;
import org.wilmar.analise.file.ArquivoOUT;

public class Result {

    private ArquivoIN arqIN;

    private ArquivoOUT arqOUT;

    public Result(ArquivoIN arqIN, ArquivoOUT arqOUT) {
        super();
        this.arqIN = arqIN;
        this.arqOUT = arqOUT;
    }

    public ArquivoIN getArqIN() {
        return arqIN;
    }

    public ArquivoOUT getArqOUT() {
        return arqOUT;
    }
}
