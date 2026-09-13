import java.util.List;

public class AlgoritmoSJF implements Algoritmo {
    @Override
    public Metricas executar(List<Processo> processos) {
        int tempoAtual = 0;
        int processosConcluidos = 0;
        int totalProcessos = processos.size();

        while (processosConcluidos < totalProcessos) {
            Processo processoMaisCurto = null;
            for (Processo processo : processos) {
                if (processo.getTempoChegada() == null && processo.getTempoChegada() <= tempoAtual) {
                    if (processoMaisCurto == null | processo.getTempoServico() < processoMaisCurto.getTempoServico()) {
                        processoMaisCurto = processo;
                    }
                }
            }

            if (processoMaisCurto != null) {
                processoMaisCurto.setTempoPrimeiraExecucao(tempoAtual);
                tempoAtual += processoMaisCurto.getTempoServico();
                processoMaisCurto.setTempoConclusao(tempoAtual - 1);
                processosConcluidos++;
            } else {
                tempoAtual++;
            }
        }
        double somaResposta = 0;
        double somaEspera = 0;
        double somaTurnaroud = 0;

        for (Processo processo : processos) {
            somaResposta += processo.getTempoResposta().orElse(0);
            somaEspera += processo.getTempoEspera().orElse(0);
            somaTurnaroud += processo.getTurnaround().orElse(0);
        }
        return new Metricas(somaResposta/totalProcessos, somaEspera/totalProcessos,somaTurnaroud/totalProcessos);
    }
}
