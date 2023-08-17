package br.com.quasar.thyaggus.download.multiplart.service;

import br.com.quasar.thyaggus.download.multiplart.entidade.Dado;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

public class MultipartService {
    private static final String SEPARADOR = ";";
    private static final String NOVA_LINHA = System.lineSeparator();
    private static final String CARSET = "UTF8";
    private static final long QUANTIDADE = 230000;


    private static List<Dado> gerarLancamentos(long inicio) {
        List<Dado> lancamentos = new LinkedList<>();
        for (long i = inicio * QUANTIDADE; i < ((inicio + 1) * QUANTIDADE); i++) {
            lancamentos.add(new Dado(
                    "13/04/2023",
                    "13/04/2023",
                    "histórico",
                    "complemento",
                    "13",
                    "BB",
                    "Origem",
                    "documento",
                    String.valueOf(i),
                    String.valueOf(i)
            ));
        }
        return lancamentos;
    }

    public static byte[] gerarLancamentosBinario(long indice) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(gerarLancamentos(indice));
        return bos.toByteArray();
    }

    public static byte[] gerarCSV(long indice) throws Exception {

        StringBuilder sb = indice == 0 ? new StringBuilder(new Dado().gerarBarraTitulo(SEPARADOR)) : new StringBuilder();
        List<Dado> lancamentos = gerarLancamentos(indice);
        lancamentos.forEach(lancamento -> sb.append(lancamento.gerarLinha(SEPARADOR)).append(NOVA_LINHA));
        return sb.toString().getBytes(CARSET);
    }
}
