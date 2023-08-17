package br.com.quasar.thyaggus.download.multiplart.arquivo;

public class Executor {
    public static void main(String[] args) {
        Arquivo arquivo = new Arquivo();
        try {
            arquivo.gerarArquivoBinárioCompleto();
            System.out.println("$> arquivo gdo com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
