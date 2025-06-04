package interface_grafica;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorDeArquivos {

    
    public static void salvarDados(String nomeArquivo, String conteudo) throws IOException {
        FileWriter writer = new FileWriter(nomeArquivo, true);
        writer.write(conteudo + "\n");
        writer.close();
    }

    public static List<String> lerDados(String nomeArquivo) throws IOException {
        List<String> dados = new ArrayList<>();
        File file = new File(nomeArquivo);

        if (!file.exists()) {
            return dados;  
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String linha;
        while ((linha = reader.readLine()) != null) {
            dados.add(linha);
        }
        reader.close();
        return dados;
    }

    public static void sobrescreverDados(String nomeArquivo, List<String> dados) throws IOException {
        FileWriter writer = new FileWriter(nomeArquivo);
        for (String linha : dados) {
            writer.write(linha + "\n");
        }
        writer.close();
    }

    public static void salvarDados(String arquivo, List<String> dadosAtualizados) {
   
        throw new UnsupportedOperationException("Unimplemented method 'salvarDados'");
    }
}
