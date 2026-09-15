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
                if (processo.concluido()) {
                    continue;
                }
                if (processo.getTempoChegada() <= tempoAtual) {
                    if (processoMaisCurto == null || processo.getTempoServico() < processoMaisCurto.getTempoServico()) {
                        processoMaisCurto = processo;
                    }
                }
            }

            if (processoMaisCurto != null) {
                processoMaisCurto.setTempoPrimeiraExecucao(tempoAtual);
                tempoAtual += processoMaisCurto.getTempoServico();
                processoMaisCurto.setTempoConclusao(tempoAtual - 1);
                processoMaisCurto.setTempoRestante(0);
                processosConcluidos++;
            } else {
                int proximoTempo = Integer.MAX_VALUE;
                for (Processo processo : processos) {
                    if (!processo.concluido() && processo.getTempoChegada() > tempoAtual) {
                        proximoTempo = Math.min(proximoTempo, processo.getTempoChegada());
                    }
                }
                tempoAtual = proximoTempo;
            }
        }
        double somaResposta = 0;
        double somaEspera = 0;
        double somaTurnaroud = 0;

        for (Processo processo : processos) {
            somaResposta += processo.getTempoResposta();
            somaEspera += processo.getTempoEspera();
            somaTurnaroud += processo.getTurnaround();
        }
        return new Metricas(somaResposta/totalProcessos, somaEspera/totalProcessos,somaTurnaroud/totalProcessos);
    }
}
