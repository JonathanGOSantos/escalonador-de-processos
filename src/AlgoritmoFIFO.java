import java.util.List;

public class AlgoritmoFIFO implements Algoritmo {
    @Override
    public Metricas executar(List<Processo> processos) {
        int tempoAtual = 0;
        for (Processo processo : processos) {
            tempoAtual = Math.max(tempoAtual, processo.getTempoChegada());
            processo.setTempoPrimeiraExecucao(tempoAtual);
            tempoAtual += processo.getTempoServico();
            processo.setTempoConclusao(tempoAtual - 1);
        }

        double somaResposta = 0;
        double somaEspera = 0;
        double somaTurnaroud = 0;

        for (Processo processo : processos) {
            somaResposta += processo.getTempoResposta().orElse(0);
            somaEspera += processo.getTempoEspera().orElse(0);
            somaTurnaroud += processo.getTurnaround().orElse(0);
        }
        int n = processos.size();
        return new Metricas(somaResposta/n, somaEspera/n,somaTurnaroud/n);
    }
}