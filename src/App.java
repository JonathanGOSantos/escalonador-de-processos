import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    // Caminho para os arquivos. Deixe vazio ("") para buscar na pasta atual (útil para o SUAP).
    // Na hora da apresentação, você pode colocar o caminho do pendrive.
    private static final String diretorio = "F:\\";
    private static final int arquivos = 10;

    public App() {
        for (int i = 1; i <= arquivos; i++) {
            try {
                String caminhoEntrada = diretorio + "TESTE-%02d.txt".formatted(i);
                List<String> linhas = lerArquivo(caminhoEntrada);
                if (linhas.isEmpty()) continue; // Skip if file doesn't exist or is empty
                int quantum = Integer.parseInt(linhas.removeFirst());
                List<Processo> processos = linhas.stream().map(l -> new Processo(Integer.parseInt(l.split(" ")[0]), Integer.parseInt(l.split(" ")[1]))).sorted(Comparator.comparingInt(Processo::getTempoChegada)).toList();

                List<Processo> processosFIFO = duplicarProcessos(processos);
                List<Processo> processosSJF = duplicarProcessos(processos);
                List<Processo> processosSRT = duplicarProcessos(processos);
                List<Processo> processosRR = duplicarProcessos(processos);

                Metricas metricaFIFO = (new AlgoritmoFIFO()).executar(processosFIFO);
                Metricas metricaSJF = (new AlgoritmoSJF()).executar(processosSJF);
                Metricas metricaSRT = (new AlgoritmoSRT()).executar(processosSRT);
                Metricas metricaRR = (new AlgoritmoRR(quantum)).executar(processosRR);
                
                String caminhoSaida = diretorio + "TESTE-%02d-RESULTADO.txt".formatted(i);
                gravarArquivo(caminhoSaida, List.of(metricaFIFO.toString(), metricaSJF.toString(), metricaSRT.toString(), metricaRR.toString()));
            } catch (Exception e) {
                System.err.printf("Erro ao processar TESTE-%02d.txt: %s\n", i, e.getMessage());
            }
        }
    }

    private static List<Processo> duplicarProcessos(List<Processo> processos) {
        return processos.stream().map(p -> new Processo(p.getTempoChegada(), p.getTempoServico())).collect(Collectors.toList());
    }

    public List<String> lerArquivo(String caminho) {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    linhas.add(linha.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo " + caminho + ": " + e.getMessage());
        }
        return linhas;
    }

    public void gravarArquivo(String caminho, List<String> linhas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for (String linha : linhas) {
                bw.write(linha + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar o arquivo " + caminho + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new App();
    }
}