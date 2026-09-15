import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AlgoritmoRR implements Algoritmo {
    private int quantum;

    public AlgoritmoRR(int quantum) {
        if (quantum <= 0) {
            throw new IllegalArgumentException("Quantum deve ser maior que 0");
        }
        this.quantum = quantum;
    }

    @Override
    public Metricas executar(List<Processo> processos) {
        Queue<Processo> fila = new LinkedList<>();
        AtomicInteger indiceProximoProcesso = new AtomicInteger(0);
        int tempo = 0;
        int execucoes = 0;
        while (indiceProximoProcesso.get() < processos.size() || !fila.isEmpty()) {
            fila.addAll(Processo.getProcessosEm(processos, tempo, indiceProximoProcesso));
            Processo primeiro = fila.peek();
            if (primeiro != null) {
                primeiro.setTempoPrimeiraExecucao(tempo);
                primeiro.executar();
                execucoes++;
                if (primeiro.concluido()) {
                    fila.remove(primeiro);
                    primeiro.setTempoConclusao(tempo);
                    execucoes = 0;
                } else if (execucoes == quantum) {
                    fila.remove(primeiro);
                    execucoes = 0;
                    fila.add(primeiro);
                }
            } else if (indiceProximoProcesso.get() < processos.size()) {
                int proximoTempo = processos.get(indiceProximoProcesso.get()).getTempoChegada();
                if (proximoTempo > tempo) {
                    tempo = proximoTempo;
                    continue;
                }
            }
            tempo++;
        }

        double mediaTempoResposta = Processo.getMediaTempoResposta(processos);
        double mediaTempoEspera = Processo.getMediaTempoEspera(processos);
        double mediaTurnaround = Processo.getMediaTurnaround(processos);
        return new Metricas(mediaTempoResposta, mediaTempoEspera, mediaTurnaround);
    }
}
