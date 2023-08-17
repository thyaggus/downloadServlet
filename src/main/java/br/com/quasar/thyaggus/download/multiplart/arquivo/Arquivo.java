package br.com.quasar.thyaggus.download.multiplart.arquivo;

import br.com.quasar.thyaggus.download.multiplart.entidade.Dado;
import br.com.quasar.thyaggus.download.multiplart.service.MultipartService;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Arquivo {


    private static final int TOTAL_ITERACAO = 5;

    public void gerarArquivoBinárioCompleto() throws Exception {

        try (FileOutputStream fout = new FileOutputStream("binário.dat")) {
            DataOutputStream dout = new DataOutputStream(fout);


            for(int i = 0; i  < TOTAL_ITERACAO ;i++) {

                // Writing data values from memory to binary file
                dout.write(MultipartService.gerarLancamentosBinario(i * TOTAL_ITERACAO));
            }
            // Closing binary file object
            fout.close();
            dout.close();
        }
        leitura();
    }

    public void gerarArquivoTemporário() throws Exception {
        final Path path = Files.createTempFile("extrato", ".swap");
        for(int i = 0; i  < TOTAL_ITERACAO ;i++) {
            Files.write(path, MultipartService.gerarCSV(i * TOTAL_ITERACAO));
        }
        System.out.println(path.toAbsolutePath().toString());
        //path.toFile().deleteOnExit();
    }

    @SuppressWarnings("unchecked")
    public void leitura() throws IOException, ClassNotFoundException {
        long start = System.currentTimeMillis();
        byte[] allBytes = Files.readAllBytes(Paths.get("binário.dat"));
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(allBytes));
        LinkedList<Dado> list = (LinkedList<Dado>) ois.readObject();
        list.forEach(System.out::println);
        long end = System.currentTimeMillis();
        System.out.println("Copied in " + (end - start) + " ms");
        ois.close();
    }
}
