import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class GeradorDeTeste {
    public static void main(String[] args) {
        String nomeArquivo = "TESTE-08.txt";
        int numProcessos = 10000;
        int quantum = 15; // Valor do 'r' escolhido para o Round-Robin

        Random rand = new Random();
        int[] chegadas = new int[numProcessos];

        // Gera 10.000 tempos de chegada aleatórios (0 a 1.000.000)
        for (int i = 0; i < numProcessos; i++) {
            chegadas[i] = rand.nextInt(1000001);
        }

        // Ordena cronologicamente para simular a fila real
        Arrays.sort(chegadas);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo))) {
            // Escreve a primeira linha (quantum)
            bw.write(quantum + "\n");

            // Escreve as 10.000 linhas de processos
            for (int i = 0; i < numProcessos; i++) {
                int duracao = rand.nextInt(1000) + 1; // 1 a 1000
                bw.write(chegadas[i] + " " + duracao + "\n");
            }

            System.out.println("✅ Arquivo " + nomeArquivo + " gerado com sucesso!");
            System.out.println("Tamanho: " + numProcessos + " processos.");

        } catch (IOException e) {
            System.err.println("Erro ao gerar arquivo: " + e.getMessage());
        }
    }
}